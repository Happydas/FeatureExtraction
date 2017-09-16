package dbpedia.vec.format.docIterator;

public interface LabelAwareDocumentIterator extends DocumentIterator {


    /**
     * Returns the current label
     * @return
     */
    String currentLabel();

}
