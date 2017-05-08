package com.ontotext.cesliteralresponsefilter.filter;

import com.ontotext.docio.model.*;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

/** **/
public class CESLiteralResponseFilter implements ContainerResponseFilter {

    CESLiteralResponseFilterFilterService cesLiteralResponseFilterFilterService;

    public CESLiteralResponseFilter(CESLiteralResponseFilterFilterService cesLiteralResponseFilterFilterService) {
        this.cesLiteralResponseFilterFilterService = cesLiteralResponseFilterFilterService;
    }

    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        System.out.println("hello");

        if (containerRequestContext.getUriInfo().getPath().endsWith("extract")) {
            Document responseDocument = (Document)containerResponseContext.getEntity();
            responseDocument = this.cesLiteralResponseFilterFilterService.addLiteralFeaturesToDocument(responseDocument);
            containerResponseContext.setEntity(responseDocument);
        }
    }

    public CESLiteralResponseFilterFilterService getCesLiteralResponseFilterFilterService() {
        return cesLiteralResponseFilterFilterService;
    }

    public void setCesLiteralResponseFilterFilterService(CESLiteralResponseFilterFilterService cesLiteralResponseFilterFilterService) {
        this.cesLiteralResponseFilterFilterService = cesLiteralResponseFilterFilterService;
    }
}
