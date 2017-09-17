package dbpedia.vec.format.senteneiterator.labelaware;

import dbpedia.vec.format.senteneiterator.SentenceIterator;

import java.util.List;


public interface LabelAwareSentenceIterator extends SentenceIterator {
    /**
     * Returns the current label for nextSentence()
     * @return the label for nextSentence()
     */
    String currentLabel();


    /**
     * For multi label problems
     * @return
     */
    List<String> currentLabels();

}