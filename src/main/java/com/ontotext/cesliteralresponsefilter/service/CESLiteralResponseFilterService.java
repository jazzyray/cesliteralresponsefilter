package com.ontotext.cesliteralresponsefilter.service;

import com.ontotext.cesliteralresponsefilter.util.ResourceUtil;
import com.ontotext.docio.DocumentIOException;
import com.ontotext.docio.DocumentIOJson;
import com.ontotext.docio.model.Document;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/** **/
public class CESLiteralResponseFilterService {

    public static final String CONTENT_JSON_FILENAME = "document/ces-json-document-request.json";
    public static final String CONTENT_JSON = ResourceUtil.getResourceFileAsString(CONTENT_JSON_FILENAME);

    public Document contentDocument;

    public CESLiteralResponseFilterService() {
        DocumentIOJson jsonDoc = new DocumentIOJson();
        try {
            contentDocument = jsonDoc.read(new ByteArrayInputStream(CONTENT_JSON.getBytes(StandardCharsets.UTF_8)));
        } catch (DocumentIOException dioe) {
            throw new RuntimeException(dioe);
        }
    }

    public Document getContentDocument() {
        return this.contentDocument;
    }


    public Document echoContentDocument(Document document) {
        return document;
    }
}
