package dbpedia.vec.it.globe.count;

import dbpedia.vec.it.sequencevector.series.SequenceElement;


public interface CoOccurrenceWriter<T extends SequenceElement> {

    /**
     * This method implementations should write out objects immediately
     * @param object
     */
    void writeObject(CoOccurrenceWeight<T> object);

    /**
     * This method implementations should queue objects for writing out.
     *
     * @param object
     */
    void queueObject(CoOccurrenceWeight<T> object);

    /**
     * Implementations of this method should close everything they use, before eradication
     */
    void finish();
}