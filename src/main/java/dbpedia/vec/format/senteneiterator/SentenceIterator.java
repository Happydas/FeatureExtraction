package dbpedia.vec.format.senteneiterator;



public interface SentenceIterator {


    String nextSentence();

    /**
     * Same idea as {@link java.util.Iterator}
     * @return whether there's anymore sentences left
     */
    boolean hasNext();

    /**
     * Resets the iterator to the beginning
     */
    void reset();

    /**
     * Allows for any finishing (closing of input streams or the like)
     */
    void finish();


    SentencePreProcessor getPreProcessor();

    void setPreProcessor(SentencePreProcessor preProcessor);


}