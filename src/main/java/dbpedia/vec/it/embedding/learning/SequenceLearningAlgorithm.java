package dbpedia.vec.it.embedding.learning;


import dbpedia.vec.it.embedding.WeightLookupTable;
import dbpedia.vec.it.embedding.words.VectorsConfiguration;
import dbpedia.vec.it.sequencevector.intface.SequenceIterator;
import dbpedia.vec.it.sequencevector.series.Sequence;
import dbpedia.vec.it.sequencevector.series.SequenceElement;
import dbpedia.vec.it.word2vec.wordrepo.VocabCache;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementations of this interface should contain sequence-related learning algorithms. Like dbow or dm.
 *
 * @author raver119@gmail.com
 */
public interface SequenceLearningAlgorithm<T extends SequenceElement> {

    String getCodeName();

    void configure(VocabCache<T> vocabCache, WeightLookupTable<T> lookupTable, VectorsConfiguration configuration);

    void pretrain(SequenceIterator<T> iterator);

    /**
     * This method does training over the sequence of elements passed into it
     *
     * @param sequence
     * @param nextRandom
     * @param learningRate
     * @return average score for this sequence
     */
    double learnSequence(Sequence<T> sequence, AtomicLong nextRandom, double learningRate);

    boolean isEarlyTerminationHit();

    /**
     * This method does training on previously unseen paragraph, and returns inferred vector
     *
     * @param sequence
     * @param nextRandom
     * @param learningRate
     * @return
     */
    INDArray inferSequence(Sequence<T> sequence, long nextRandom, double learningRate, double minLearningRate,
                           int iterations);

    ElementsLearningAlgorithm<T> getElementsLearningAlgorithm();

    void finish();
}