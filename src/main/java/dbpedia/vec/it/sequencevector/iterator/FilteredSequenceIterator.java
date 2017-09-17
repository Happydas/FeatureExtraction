package dbpedia.vec.it.sequencevector.iterator;


import lombok.NonNull;
import dbpedia.vec.it.sequencevector.intface.SequenceIterator;
import dbpedia.vec.it.sequencevector.series.Sequence;
import dbpedia.vec.it.sequencevector.series.SequenceElement;
import dbpedia.vec.it.word2vec.wordrepo.VocabCache;


public class FilteredSequenceIterator<T extends SequenceElement> implements SequenceIterator<T> {

    private final SequenceIterator<T> underlyingIterator;
    private final VocabCache<T> vocabCache;

    /**
     * Creates Filtered SequenceIterator on top of another SequenceIterator and appropriate VocabCache instance
     *
     * @param iterator
     * @param vocabCache
     */
    public FilteredSequenceIterator(@NonNull SequenceIterator<T> iterator, @NonNull VocabCache<T> vocabCache) {
        this.vocabCache = vocabCache;
        this.underlyingIterator = iterator;
    }

    /**
     * Checks, if there's any more sequences left in underlying iterator
     * @return
     */
    @Override
    public boolean hasMoreSequences() {
        return underlyingIterator.hasMoreSequences();
    }

    /**
     * Returns filtered sequence, that contains sequence elements from vocabulary only.
     * Please note: it can return empty sequence, if no elements were found in vocabulary
     * @return
     */
    @Override
    public Sequence<T> nextSequence() {
        Sequence<T> originalSequence = underlyingIterator.nextSequence();
        Sequence<T> newSequence = new Sequence<>();

        if (originalSequence != null)
            for (T element : originalSequence.getElements()) {
                if (element != null && vocabCache.hasToken(element.getLabel())) {
                    newSequence.addElement(vocabCache.wordFor(element.getLabel()));
                }
            }

        newSequence.setSequenceId(originalSequence.getSequenceId());

        return newSequence;
    }

    /**
     * Resets iterator down to first sequence
     */
    @Override
    public void reset() {
        underlyingIterator.reset();
    }
}