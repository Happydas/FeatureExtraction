package dbpedia.vec.format.senteneiterator;

import lombok.NonNull;


public class SynchronizedSentenceIterator implements SentenceIterator {
    private SentenceIterator underlyingIterator;

    public SynchronizedSentenceIterator(@NonNull SentenceIterator iterator) {
        this.underlyingIterator = iterator;
    }

    @Override
    public synchronized String nextSentence() {
        return this.underlyingIterator.nextSentence();
    }

    @Override
    public synchronized boolean hasNext() {
        return underlyingIterator.hasNext();
    }

    @Override
    public synchronized void reset() {
        this.underlyingIterator.reset();
    }

    @Override
    public synchronized void finish() {
        this.underlyingIterator.finish();
    }

    @Override
    public synchronized SentencePreProcessor getPreProcessor() {
        return this.underlyingIterator.getPreProcessor();
    }

    @Override
    public synchronized void setPreProcessor(SentencePreProcessor preProcessor) {
        this.underlyingIterator.setPreProcessor(preProcessor);
    }
}