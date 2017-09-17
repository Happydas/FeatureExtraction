package dbpedia.vec.it.globe.count;


import dbpedia.vec.it.sequencevector.series.SequenceElement;


public interface CoOccurenceReader<T extends SequenceElement> {
    /*
        Storage->Memory merging part
     */
    boolean hasMoreObjects();


    CoOccurrenceWeight<T> nextObject();

    void finish();
}