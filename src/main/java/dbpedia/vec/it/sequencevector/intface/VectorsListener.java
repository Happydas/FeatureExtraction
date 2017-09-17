package dbpedia.vec.it.sequencevector.intface;

import dbpedia.vec.it.sequencevector.SequenceVectors;
import dbpedia.vec.it.sequencevector.enums.ListenerEvent;
import dbpedia.vec.it.sequencevector.series.SequenceElement;


public interface VectorsListener<T extends SequenceElement> {

    /**
     * This method is called prior each processEvent call, to check if this specific VectorsListener implementation is viable for specific event
     *
     * @param event
     * @param argument
     * @return TRUE, if this event can and should be processed with this listener, FALSE otherwise
     */
    boolean validateEvent(ListenerEvent event, long argument);

    /**
     * This method is called at each epoch end
     *
     * @param event
     * @param sequenceVectors
     */
    void processEvent(ListenerEvent event, SequenceVectors<T> sequenceVectors, long argument);
}