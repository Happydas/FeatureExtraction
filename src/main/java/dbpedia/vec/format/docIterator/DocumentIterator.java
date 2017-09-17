package dbpedia.vec.format.docIterator;

import java.io.InputStream;
import java.io.Serializable;




public interface DocumentIterator extends Serializable {


    InputStream nextDocument();

    /**
     * Whether there are anymore documents in the iterator
     * @return whether there are anymore documents
     * in the iterator
     */
    boolean hasNext();

    /**
     * Reset the iterator to the beginning
     */
    void reset();



}