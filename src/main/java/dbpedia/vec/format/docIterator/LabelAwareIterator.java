package dbpedia.vec.format.docIterator;


import java.util.Iterator;

public interface LabelAwareIterator extends Iterator<LabelledDocument> {

    boolean hasNextDocument();

    LabelledDocument nextDocument();

    void reset();

    LabelsSource getLabelsSource();

    void shutdown();
}