package dbpedia.vec.it.sequencevector.intface;

import dbpedia.vec.it.sequencevector.series.SequenceElement;

/**
 * This is interface for JSON -> SequenceElement serialization/deserialziation
 *
 * @author raver119@gmail.com
 */
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
