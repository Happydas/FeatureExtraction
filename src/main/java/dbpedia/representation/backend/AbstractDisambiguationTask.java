package dbpedia.representation.backend;

import dbpedia.representation.dpo.Response;
import dbpedia.representation.vector.AbstractKnowledgeBase;
import dbpedia.representation.vector.KnowledgeBaseIdentifiers;

import java.util.List;

public abstract class AbstractDisambiguationTask {

    protected int returnNr;

    protected AbstractKnowledgeBase kb;

    protected KnowledgeBaseIdentifiers kbIdentifier;

    protected boolean retrieveDocClasses;

    protected List<Response> responses;

    public  int getReturnNr() {
        return returnNr;
    }

    public  void setReturnNr(int returnNr) {
        this.returnNr = returnNr;
    }

    public  AbstractKnowledgeBase getKb() {
        return kb;
    }

    public  void setKb(AbstractKnowledgeBase kb) {
        this.kb = kb;
    }

    public  KnowledgeBaseIdentifiers getKbIdentifier() {
        return this.kbIdentifier;
    }

    public  boolean isRetrieveDocClasses() {
        return retrieveDocClasses;
    }

    public  void setRetrieveDocClasses(boolean retrieveDocClasses) {
        this.retrieveDocClasses = retrieveDocClasses;
    }

    public  List<Response> getResponse() {
        return responses;
    }

    public  void setResponse(List<Response> responses) {
        this.responses = responses;
    }

    public abstract  void setKbIdentifier(String kbversion, String setting);
}
