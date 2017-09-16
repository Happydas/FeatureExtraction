package dbpedia.vec.format.senteneiterator.labelaware;

import dbpedia.vec.format.senteneiterator.SentenceIterator;

import java.util.List;

/**
 * SentenceIterator that is aware of its label. This is useful
 * for creating datasets all at once or on the fly.
 *
 * @author Adam Gibson
 */
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