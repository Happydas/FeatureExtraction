package dbpedia.vec.it.sequencevector.iterator;


import lombok.NonNull;
import dbpedia.vec.it.sequencevector.intface.SequenceIterator;
import dbpedia.vec.it.sequencevector.series.Sequence;
import dbpedia.vec.it.sequencevector.series.SequenceElement;

/**
 * Synchronized version of AbstractSeuqenceIterator, implemented on top of it.
 * Suitable for cases with non-strict multithreading environment, since it's just synchronized wrapper
 *
 * @author raver119@gmail.com
 */
public class SynchronizedSequenceIterator<T extends SequenceElement> implements SequenceIterator<T> {
    protected SequenceIterator<T> underlyingIterator;

    /**
     * Creates SynchronizedSequenceIterator on top of any SequenceIterator
     * @param iterator
     */
    public SynchronizedSequenceIterator(@NonNull SequenceIterator<T> iterator) {
        this.underlyingIterator = iterator;
    }

    /**
     * Checks, if there's any more sequences left in data source
     * @return
     */
    @Override
    public synchronized boolean hasMoreSequences() {
        return underlyingIterator.hasMoreSequences();
    }

    /**
     * Returns next sequence from data source
     *
     * @return
     */
    @Override
    public synchronized Sequence<T> nextSequence() {
        return underlyingIterator.nextSequence();
    }

    /**
     * This method resets underlying iterator
     */
    @Override
    public synchronized void reset() {
        underlyingIterator.reset();
    }
}