package dbpedia.vec.it.embedding.load;

import dbpedia.vec.it.embedding.WeightLookupTable;
import dbpedia.vec.it.sequencevector.series.SequenceElement;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Instances implementing this interface should be responsible for utility access to SequenceVectors model
 *
 * @author raver119@gmail.com
 */
public interface ModelUtils<T extends SequenceElement> {

    /**
     * This method implementations should accept given lookup table, and use them in further calls to interface methods
     *
     * @param lookupTable
     */
    void init(WeightLookupTable<T> lookupTable);

    /**
     * This method implementations should return distance between two given elements
     *
     * @param label1
     * @param label2
     * @return
     */
    double similarity(String label1, String label2);


    /**
     * Accuracy based on questions which are a space separated list of strings
     * where the first word is the query word, the next 2 words are negative,
     * and the last word is the predicted word to be nearest
     * @param questions the questions to ask
     * @return the accuracy based on these questions
     */
    Map<String, Double> accuracy(List<String> questions);


    /**
     * Find all words with a similar characters
     * in the vocab
     * @param word the word to compare
     * @param accuracy the accuracy: 0 to 1
     * @return the list of words that are similar in the vocab
     */
    List<String> similarWordsInVocabTo(String word, double accuracy);


    /**
     * This method implementations should return N nearest elements labels to given element's label
     *
     * @param label label to return nearest elements for
     * @param n number of nearest words to return
     * @return
     */
    Collection<String> wordsNearest(String label, int n);

    /**
     * Words nearest based on positive and negative words
     *
     * @param positive the positive words
     * @param negative the negative words
     * @param top the top n words
     * @return the words nearest the mean of the words
     */
    Collection<String> wordsNearest(Collection<String> positive, Collection<String> negative, int top);


    /**
     * Words nearest based on positive and negative words
     * * @param top the top n words
     * @return the words nearest the mean of the words
     */
    Collection<String> wordsNearest(INDArray words, int top);


    Collection<String> wordsNearestSum(String word, int n);


    Collection<String> wordsNearestSum(INDArray words, int top);

    Collection<String> wordsNearestSum(Collection<String> positive, Collection<String> negative, int top);
}