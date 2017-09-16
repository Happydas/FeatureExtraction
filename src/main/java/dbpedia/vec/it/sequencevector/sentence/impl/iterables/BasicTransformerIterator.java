package dbpedia.vec.it.sequencevector.sentence.impl.iterables;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import dbpedia.vec.it.sequencevector.series.Sequence;
import dbpedia.vec.it.sequencevector.sentence.impl.SentenceTransformer;
import dbpedia.vec.it.word2vec.VocabWord;
import dbpedia.vec.format.docIterator.LabelAwareIterator;
import dbpedia.vec.format.docIterator.LabelledDocument;

import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
@Slf4j
public class BasicTransformerIterator implements Iterator<Sequence<VocabWord>> {
    protected final LabelAwareIterator iterator;
    protected boolean allowMultithreading;
    protected final SentenceTransformer sentenceTransformer;

    public BasicTransformerIterator(@NonNull LabelAwareIterator iterator, @NonNull SentenceTransformer transformer) {
        this.iterator = iterator;
        this.allowMultithreading = false;
        this.sentenceTransformer = transformer;

        this.iterator.reset();
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNextDocument();
    }

    @Override
    public Sequence<VocabWord> next() {
        LabelledDocument document = iterator.nextDocument();
        if (document == null || document.getContent() == null)
            return new Sequence<>();
        Sequence<VocabWord> sequence = sentenceTransformer.transformToSequence(document.getContent());

        if (document.getLabels() != null)
            for (String label : document.getLabels()) {
                if (label != null && !label.isEmpty())
                    sequence.addSequenceLabel(new VocabWord(1.0, label));
            }
        /*
        if (document.getLabel() != null && !document.getLabel().isEmpty()) {
            sequence.setSequenceLabel(new VocabWord(1.0, document.getLabel()));
        }*/

        return sequence;
    }


    public void reset() {
        //
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
