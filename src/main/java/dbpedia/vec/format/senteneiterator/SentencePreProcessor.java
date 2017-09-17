package dbpedia.vec.format.senteneiterator;


import java.io.Serializable;


public interface SentencePreProcessor extends Serializable {

    /**
     * Pre process a sentence
     * @param sentence the sentence to pre process
     * @return the pre processed sentence
     */
    String preProcess(String sentence);

}