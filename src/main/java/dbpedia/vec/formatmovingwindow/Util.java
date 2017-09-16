package dbpedia.vec.formatmovingwindow;

import org.nd4j.linalg.primitives.Counter;
import org.nd4j.linalg.primitives.CounterMap;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Util {


    private Util() {}

    /**
     * Returns a thread safe counter map
     * @return
     */
    public static <K, V> CounterMap<K, V> parallelCounterMap() {
        CounterMap<K, V> totalWords = new CounterMap<>();
        return totalWords;
    }


    /**
     * Returns a thread safe counter
     * @return
     */
    public static <K> Counter<K> parallelCounter() {
        Counter<K> totalWords = new Counter<>();
        return totalWords;
    }



    public static boolean matchesAnyStopWord(List<String> stopWords, String word) {
        for (String s : stopWords)
            if (s.equalsIgnoreCase(word))
                return true;
        return false;
    }

    public static Level disableLogging() {
        Logger logger = Logger.getLogger("org.apache.uima");
        while (logger.getLevel() == null) {
            logger = logger.getParent();
        }
        Level level = logger.getLevel();
        logger.setLevel(Level.OFF);
        return level;
    }


}