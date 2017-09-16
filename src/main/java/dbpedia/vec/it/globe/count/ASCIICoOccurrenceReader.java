package dbpedia.vec.it.globe.count;


import lombok.NonNull;
import dbpedia.vec.it.sequencevector.series.SequenceElement;
import dbpedia.vec.it.word2vec.wordrepo.VocabCache;
import dbpedia.vec.format.senteneiterator.BasicLineIterator;
import dbpedia.vec.format.senteneiterator.PrefetchingSentenceIterator;
import dbpedia.vec.format.senteneiterator.SentenceIterator;

import java.io.File;
import java.io.PrintWriter;

/**
 * @author raver119@gmail.com
 */
public class ASCIICoOccurrenceReader<T extends SequenceElement> implements CoOccurenceReader<T> {
    private File file;
    private PrintWriter writer;
    private SentenceIterator iterator;
    private VocabCache<T> vocabCache;

    public ASCIICoOccurrenceReader(@NonNull File file, @NonNull VocabCache<T> vocabCache) {
        this.vocabCache = vocabCache;
        this.file = file;
        try {
            iterator = new PrefetchingSentenceIterator.Builder(new BasicLineIterator(file)).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean hasMoreObjects() {
        return iterator.hasNext();
    }


    /**
     * Returns next CoOccurrenceWeight object
     *
     * PLEASE NOTE: This method can return null value.
     * @return
     */
    @Override
    public CoOccurrenceWeight<T> nextObject() {
        String line = iterator.nextSentence();
        if (line == null || line.isEmpty()) {
            return null;
        }
        String[] strings = line.split(" ");

        CoOccurrenceWeight<T> object = new CoOccurrenceWeight<>();
        object.setElement1(vocabCache.elementAtIndex(Integer.valueOf(strings[0])));
        object.setElement2(vocabCache.elementAtIndex(Integer.valueOf(strings[1])));
        object.setWeight(Double.parseDouble(strings[2]));

        return object;
    }



    @Override
    public void finish() {
        try {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}