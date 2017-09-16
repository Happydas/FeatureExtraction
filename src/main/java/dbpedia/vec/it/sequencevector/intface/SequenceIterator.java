package dbpedia.vec.it.sequencevector.intface;

import dbpedia.vec.it.sequencevector.series.Sequence;
import dbpedia.vec.it.sequencevector.series.SequenceElement;

/**
 * SequenceIterator is basic interface for learning abstract data that can be represented as sequence of some elements.
 *
 * @author raver119@gmail.com
 */
public interface SequenceIterator<T extends SequenceElement> {

    boolean hasMoreSequences();

    Sequence<T> nextSequence();

    void reset();
}
