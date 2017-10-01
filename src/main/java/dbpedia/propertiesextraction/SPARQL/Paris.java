package dbpedia.propertiesextraction.SPARQL;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

public class Paris {
    public static void main(String[] args) {

        String queryString=
                "dbp: <http://dbpedia.org/property/>\n" +
                        "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                        "PREFIX s: <http://schema.org/>\n" +
                        "PREFIX dbr: <http://dbpedia.org/resource/>" +
                        "SELECT DISTINCT * WHERE {\n" +
                        "?h a s:Hotel .\n" +
                        "?h dbo:location dbr:Italy  .\n" +
                        "}";

// now creating query object
        Query query = QueryFactory.create(queryString);
// initializing queryExecution factory with remote service.
// **this actually was the main problem I couldn't figure out.**
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

//after it goes standard query execution and result processing which can
// be found in almost any Jena/SPARQL tutorial.
        try {
            ResultSet results = qexec.execSelect();
            for (; results.hasNext();) {

                // Result processing is done here.
            }
        }
        finally {
            qexec.close();
        }
    }
}
