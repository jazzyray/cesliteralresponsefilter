package com.ontotext.cesliteralresponsefilter.filter;


import com.ontotext.cesliteralresponsefilter.exception.CESLiteralResponseFilterRepositoryException;
import com.ontotext.cesliteralresponsefilter.model.PredicateLiteral;
import com.ontotext.cesliteralresponsefilter.util.ResourceUtil;
import com.ontotext.docio.model.*;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFHandler;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.zip.GZIPInputStream;

/** **/
public class CESLiteralResponseFilterFilterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CESLiteralResponseFilterFilterService.class);

    private static final String QUERY = "select  ?subject ?predicate ?literal { \n" +
            "  ?subject ?predicate ?literal .\n" +
            "  filter isLiteral(?literal)\n" +
            "}";

    private static final String DATA_PATH = "data/dump.ttl.gz";

    private static Repository repository;

    private Map<String, List<PredicateLiteral>> concepts;

    public CESLiteralResponseFilterFilterService() {
        repository = new SailRepository(new MemoryStore());
        repository.initialize();
        fillRepository(DATA_PATH);
        fetchLiterals();
    }

    public CESLiteralResponseFilterFilterService(String dataPath) {
        repository = new SailRepository(new MemoryStore());
        repository.initialize();
        fillRepository(dataPath);
        fetchLiterals();
    }

    private static void fillRepository(String dumpPath) {
        try (RepositoryConnection connection = repository.getConnection()) {
            RDFParser parser = Rio.createParser(RDFFormat.TURTLE);
            parser.setRDFHandler(new RDFHandler() {
                @Override public void startRDF() {}

                @Override public void endRDF() {}

                @Override public void handleNamespace(String prefix, String uri) {}

                @Override public void handleStatement(Statement statement) {
                    connection.add(statement);
                }

                @Override public void handleComment(String comment) {}
            });

            try {
                parser.parse(new GZIPInputStream(ResourceUtil.getResourceFileAsStream(dumpPath)), "http://ontotest.com");
            } catch (IOException io) {
                throw new CESLiteralResponseFilterRepositoryException(io);
            }
        }
    }

    public static Repository getRepository() {
        return repository;
    }

    public void fetchLiterals() {
        LOGGER.info("fetching concepts from repository");
        concepts = new HashMap<>();
        try (RepositoryConnection connection = repository.getConnection()) {
            LOGGER.info("executing query:\n" + QUERY);
            TupleQuery query = connection.prepareTupleQuery(QueryLanguage.SPARQL, QUERY);
            try (TupleQueryResult result = query.evaluate()) {
                while (result.hasNext()) {
                    BindingSet bindings = result.next();
                    String concept = bindings.getValue("subject").stringValue();
                    List<PredicateLiteral> literals = (concepts.containsKey(concept)) ? concepts.get(concept) : new ArrayList<>();
                    String predicate = bindings.getValue("predicate").stringValue();
                    String literalValue = bindings.getValue("literal").stringValue();
                    Optional<String> lang =((Literal)bindings.getValue("literal")).getLanguage();
                    IRI type = ((Literal)bindings.getValue("literal")).getDatatype();
                    literals.add(new PredicateLiteral(predicate, literalValue, type, lang));
                    concepts.put(concept,literals);
                }
            } catch (Exception e) {
                LOGGER.error("failed to fetch concepts from repository" + QUERY, e);
            }
        }
        LOGGER.info("concepts fetched: " + concepts.size());
    }

    public Document addLiteralFeaturesToDocument(Document document) {
        for (AnnotationSet annotationSet : document.getAnnotationSets()) {
            for (Annotation annotation : annotationSet.getAnnotations()) {

                 Feature instFeature = annotation.getFeatureForName("inst");
                 String instUri = instFeature.getValue().getValue();

                 List<PredicateLiteral> literals = concepts.get(instUri);

                 for (PredicateLiteral literal : literals) {
                     Feature literalFeature = new Feature();
                     Name name = new Name();
                     name.setName(literal.getPredicate());
                     name.setType(NameType.XS_ANY_URI);
                     literalFeature.setName(name);

                     Value value = new Value();
                     value.setValue(literal.getLiteral());
                     value.setType(literal.getLiteralType());
                     if (literal.getLang().isPresent()) {
                         value.setLang(literal.getLang().get().toString());
                     }
                     literalFeature.setValue(value);

                     annotation.getFeatures().add(literalFeature);
                 }
            }
        }
        return document;
    }

}
