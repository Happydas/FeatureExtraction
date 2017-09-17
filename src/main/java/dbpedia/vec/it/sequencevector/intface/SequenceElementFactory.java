package dbpedia.vec.it.sequencevector.intface;

import dbpedia.vec.it.sequencevector.series.SequenceElement;


public interface SequenceElementFactory<T extends SequenceElement> {
    /**
     * This method builds object from provided JSON
     *
     * @param json JSON for restored object
     * @return restored object
     */
    T deserialize(String json);

    /**
     * This method serializaes object  into JSON string
     * @param element
     * @return
     */
    String serialize(T element);
}
