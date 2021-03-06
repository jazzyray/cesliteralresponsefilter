package com.ontotext.cesliteralresponsefilter.model;

import com.ontotext.docio.model.Value;
import com.ontotext.docio.model.ValueType;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

import java.util.Optional;

/** **/
public class PredicateLiteral {

    private String predicate;
    private String literal;
    private ValueType literalType;
    private Optional<String> lang;

    public PredicateLiteral(String predicate, String literal, IRI literalType, Optional<String> lang) {
        this.predicate = predicate;
        this.literal = literal;
        setLiteralType(literalType);
        this.lang = lang;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    public ValueType getLiteralType() {
        return this.literalType;
    }

    public void setLiteralType(IRI literalType) {
        if (literalType.equals(RDF.LANGSTRING) || literalType.equals(XMLSchema.STRING)) {
            this.literalType = ValueType.XS_STRING;
        } else if (literalType.equals(XMLSchema.BOOLEAN)) {
            this.literalType = ValueType.XS_BOOLEAN;
        } else if (literalType.equals(XMLSchema.DOUBLE)) {
            this.literalType = ValueType.XS_DOUBLE;
        } else if (literalType.equals(XMLSchema.LONG)) {
            this.literalType = ValueType.XS_LONG;
        } else if (literalType.equals(XMLSchema.DATETIME)) {
            this.literalType = ValueType.XS_DATE;
        } else if (literalType.equals(XMLSchema.INTEGER)) {
            this.literalType = ValueType.XS_INTEGER;
        } else {
            this.literalType = ValueType.XS_STRING;
        }
    }

    public void setLiteralType(ValueType literalType) {
        this.literalType = literalType;
    }

    public Optional<String> getLang() {
        return lang;
    }

    public void setLang(Optional<String> lang) {
        this.lang = lang;
    }
}
