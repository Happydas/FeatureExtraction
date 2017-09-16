package dbpedia.vec.it.embedding.learning.alg.sequence;

import lombok.NonNull;
import dbpedia.vec.it.embedding.WeightLookupTable;
import dbpedia.vec.it.embedding.learning.ElementsLearningAlgorithm;
import dbpedia.vec.it.embedding.learning.SequenceLearningAlgorithm;
import dbpedia.vec.it.embedding.learning.alg.implementation.SkipGram;
import dbpedia.vec.it.embedding.words.VectorsConfiguration;
import dbpedia.vec.it.sequencevector.intface.SequenceIterator;
import dbpedia.vec.it.sequencevector.series.Sequence;
import dbpedia.vec.it.sequencevector.series.SequenceElement;
import dbpedia.vec.it.word2vec.wordrepo.VocabCache;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.rng.Random;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author raver119@gmail.com
 */
public class DBOW<T extends SequenceElement> implements SequenceLearningAlgorithm<T> {
    protected VocabCache<T> vocabCache;
    protected WeightLookupTable<T> lookupTable;
    protected VectorsConfiguration configuration;


    protected int window;
    protected boolean useAdaGrad;
    protected double negative;

    protected SkipGram<T> skipGram = new SkipGram<>();

    private static final Logger log = LoggerFactory.getLogger(DBOW.class);

    @Override
    public ElementsLearningAlgorithm<T> getElementsLearningAlgorithm() {
        return skipGram;
    }

    public DBOW() {

    }

    @Override
    public String getCodeName() {
        return "PV-DBOW";
    }

    @Override
    public void configure(@NonNull VocabCache<T> vocabCache, @NonNull WeightLookupTable<T> lookupTable,
                          @NonNull VectorsConfiguration configuration) {
        this.vocabCache = vocabCache;
        this.lookupTable = lookupTable;

        this.window = configuration.getWindow();
        this.useAdaGrad = configuration.isUseAdaGrad();
        this.negative = configuration.getNegative();
        this.configuration = configuration;

        skipGram.configure(vocabCache, lookupTable, configuration);
    }

    /**
     * DBOW doesn't involves any pretraining
     *
     * @param iterator
     */
    @Override
    public void pretrain(SequenceIterator<T> iterator) {

    }

    @Override
    public double learnSequence(@NonNull Sequence<T> sequence, @NonNull AtomicLong nextRandom, double learningRate) {

        // we just pass data to dbow, and loop over sequence there
        dbow(0, sequence, (int) nextRandom.get() % window, nextRandom, learningRate, false, null);


        return 0;
    }

    /**
     * DBOW has no reasons for early termination
     * @return
     */
    @Override
    public boolean isEarlyTerminationHit() {
        return false;
    }

    protected void dbow(int i, Sequence<T> sequence, int b, AtomicLong nextRandom, double alpha, boolean isInference,
                        INDArray inferenceVector) {

        //final T word = sequence.getElements().get(i);
        List<T> sentence = skipGram.applySubsampling(sequence, nextRandom).getElements();


        if (sequence.getSequenceLabel() == null)
            return;

        List<T> labels = new ArrayList<>();
        labels.addAll(sequence.getSequenceLabels());

        if (sentence.isEmpty() || labels.isEmpty())
            return;

        for (T lastWord : labels) {
            for (T word : sentence) {
                if (word == null)
                    continue;

                skipGram.iterateSample(word, lastWord, nextRandom, alpha, isInference, inferenceVector);
            }
        }

        if (skipGram != null && skipGram.getBatch() != null && skipGram.getBatch() != null
                && skipGram.getBatch().size() >= configuration.getBatchSize()) {
            Nd4j.getExecutioner().exec(skipGram.getBatch());
            skipGram.getBatch().clear();
        }
    }

    /**
     * This method does training on previously unseen paragraph, and returns inferred vector
     *
     * @param sequence
     * @param nextRandom
     * @param learningRate
     * @return
     */
    @Override
    public INDArray inferSequence(Sequence<T> sequence, long nextRandom, double learningRate, double minLearningRate,
                                  int iterations) {
        AtomicLong nr = new AtomicLong(nextRandom);

        // we probably don't want subsampling here
        // Sequence<T> seq = cbow.applySubsampling(sequence, nextRandom);
        // if (sequence.getSequenceLabel() == null) throw new IllegalStateException("Label is NULL");

        if (sequence.isEmpty())
            return null;


        Random random = Nd4j.getRandomFactory().getNewRandomInstance(configuration.getSeed() * sequence.hashCode(),
                lookupTable.layerSize() + 1);
        INDArray ret = Nd4j.rand(new int[] {1, lookupTable.layerSize()}, random).subi(0.5)
                .divi(lookupTable.layerSize());

        for (int iter = 0; iter < iterations; iter++) {
            nr.set(Math.abs(nr.get() * 25214903917L + 11));
            dbow(0, sequence, (int) nr.get() % window, nr, learningRate, true, ret);

            learningRate = ((learningRate - minLearningRate) / (iterations - iter)) + minLearningRate;
        }

        finish();

        return ret;
    }

    @Override
    public void finish() {
        if (skipGram != null && skipGram.getBatch() != null && skipGram.getBatch().size() > 0) {
            Nd4j.getExecutioner().exec(skipGram.getBatch());
            skipGram.getBatch().clear();
        }
    }
}