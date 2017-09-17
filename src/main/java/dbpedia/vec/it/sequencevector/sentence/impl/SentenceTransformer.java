package dbpedia.vec.it.sequencevector.sentence.impl;


import lombok.NonNull;
import dbpedia.vec.it.sequencevector.series.Sequence;
import dbpedia.vec.it.sequencevector.sentence.SequenceTransformer;
import dbpedia.vec.it.sequencevector.sentence.impl.iterables.BasicTransformerIterator;
import dbpedia.vec.it.sequencevector.sentence.impl.iterables.ParallelTransformerIterator;
import dbpedia.vec.it.word2vec.VocabWord;
import dbpedia.vec.it.word2vec.wordrepo.VocabCache;
import dbpedia.vec.format.docIterator.BasicLabelAwareIterator;
import dbpedia.vec.format.docIterator.DocumentIterator;
import dbpedia.vec.format.docIterator.LabelAwareIterator;
import dbpedia.vec.format.senteneiterator.SentenceIterator;
import dbpedia.vec.format.tokenization.tokenizer.Tokenizer;
import dbpedia.vec.format.tokenization.tokenizerfactory.TokenizerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class SentenceTransformer implements SequenceTransformer<VocabWord, String>, Iterable<Sequence<VocabWord>> {

    protected TokenizerFactory tokenizerFactory;
    protected LabelAwareIterator iterator;
    protected boolean readOnly = false;
    protected AtomicInteger sentenceCounter = new AtomicInteger(0);
    protected boolean allowMultithreading = false;
    protected BasicTransformerIterator currentIterator;

    protected static final Logger log = LoggerFactory.getLogger(SentenceTransformer.class);

    private SentenceTransformer(@NonNull LabelAwareIterator iterator) {
        this.iterator = iterator;
    }

    @Override
    public Sequence<VocabWord> transformToSequence(String object) {
        Sequence<VocabWord> sequence = new Sequence<>();

        Tokenizer tokenizer = tokenizerFactory.create(object);
        List<String> list = tokenizer.getTokens();

        for (String token : list) {
            if (token == null || token.isEmpty() || token.trim().isEmpty())
                continue;

            VocabWord word = new VocabWord(1.0, token);
            sequence.addElement(word);
        }

        sequence.setSequenceId(sentenceCounter.getAndIncrement());
        return sequence;
    }

    @Override
    public Iterator<Sequence<VocabWord>> iterator() {
        if (currentIterator != null)
            reset();

        if (!allowMultithreading)
            currentIterator = new BasicTransformerIterator(iterator, this);
        else
            currentIterator = new ParallelTransformerIterator(iterator, this, true);

        return currentIterator;
    }

    @Override
    public void reset() {
        if (currentIterator != null)
            currentIterator.reset();
    }


    public static class Builder {
        protected TokenizerFactory tokenizerFactory;
        protected LabelAwareIterator iterator;
        protected VocabCache<VocabWord> vocabCache;
        protected boolean readOnly = false;
        protected boolean allowMultithreading = false;

        public Builder() {

        }

        public Builder tokenizerFactory(@NonNull TokenizerFactory tokenizerFactory) {
            this.tokenizerFactory = tokenizerFactory;
            return this;
        }

        public Builder iterator(@NonNull LabelAwareIterator iterator) {
            this.iterator = iterator;
            return this;
        }

        public Builder iterator(@NonNull SentenceIterator iterator) {
            this.iterator = new BasicLabelAwareIterator.Builder(iterator).build();
            return this;
        }

        public Builder iterator(@NonNull DocumentIterator iterator) {
            this.iterator = new BasicLabelAwareIterator.Builder(iterator).build();
            return this;
        }

        public Builder readOnly(boolean readOnly) {
            this.readOnly = true;
            return this;
        }

        /**
         * This method enables/disables parallel processing over sentences
         *
         * @param reallyAllow
         * @return
         */
        public Builder allowMultithreading(boolean reallyAllow) {
            this.allowMultithreading = reallyAllow;
            return this;
        }

        public SentenceTransformer build() {
            SentenceTransformer transformer = new SentenceTransformer(this.iterator);
            transformer.tokenizerFactory = this.tokenizerFactory;
            transformer.readOnly = this.readOnly;
            transformer.allowMultithreading = this.allowMultithreading;

            return transformer;
        }
    }
}