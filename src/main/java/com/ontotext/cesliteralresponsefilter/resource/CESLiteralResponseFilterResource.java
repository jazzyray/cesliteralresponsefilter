package com.ontotext.cesliteralresponsefilter.resource;

import com.codahale.metrics.annotation.Timed;
import com.ontotext.cesliteralresponsefilter.service.CESLiteralResponseFilterService;
import com.ontotext.commons.web.docio.CesDocumentMediaType;
import com.ontotext.docio.model.Document;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;
import java.util.concurrent.ExecutionException;

/** **/
@Api("CES Literal Response Filter Mock API")
@Path("/worker")
public class CESLiteralResponseFilterResource {

    CESLiteralResponseFilterService cesLiteralResponseFilterService;

    public CESLiteralResponseFilterResource(CESLiteralResponseFilterService cesLiteralResponseFilterService) {
        this.cesLiteralResponseFilterService = cesLiteralResponseFilterService;
    }

    @POST
    @Timed
    @Consumes({
            CesDocumentMediaType.GENERIC_DOCUMENT_JSON_VALUE,
            CesDocumentMediaType.GENERIC_DOCUMENT_XML_VALUE,
            CesDocumentMediaType.GATE_XML_VALUE })
    @Produces({
            CesDocumentMediaType.SIMPLE_JSON_VALUE,
            CesDocumentMediaType.SIMPLE_XML_VALUE,
            CesDocumentMediaType.GENERIC_DOCUMENT_JSON_VALUE,
            CesDocumentMediaType.GENERIC_DOCUMENT_XML_VALUE,
            CesDocumentMediaType.GATE_XML_VALUE,
            CesDocumentMediaType.MATE_VALUE })
    @Path("/extract")
    @ApiOperation(value = "Extract", response = Document.class)
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid Annotated Content Id supplied"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Annotated Content not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Success",
                    responseHeaders = {@ResponseHeader(name = "X-Cache", description = "Explains whether or not a cache was used", response = Boolean.class),
                            @ResponseHeader(name = HttpHeaders.VARY, description = "Make sure proxies cache by Vary", response = String.class),
                            @ResponseHeader(name = HttpHeaders.ETAG, description = "Annotated Content ETag for cache control", response = String.class),
                            @ResponseHeader(name = HttpHeaders.ALLOW, description = "CORS Allowed Methods", response = String.class)}),
            @ApiResponse(code = HttpURLConnection.HTTP_ACCEPTED, message = "Success",
                    responseHeaders = {@ResponseHeader(name = "X-Cache", description = "Explains whether or not a cache was used", response = Boolean.class),
                            @ResponseHeader(name = HttpHeaders.VARY, description = "Make sure proxies cache by Vary", response = String.class),
                            @ResponseHeader(name = HttpHeaders.ETAG, description = "Annotated Content ETag for cache control", response = String.class),
                            @ResponseHeader(name = HttpHeaders.ALLOW, description = "CORS Allowed Methods", response = String.class)})})
    public Response annotate(
            @ApiParam(name = "timeout", required = false) @QueryParam("timeout") Long timeout,
            @ApiParam(name = "debug", required = false, defaultValue = "false")  @QueryParam("debug") boolean isDebugMode,
            @ApiParam(name = "body", value = "Document to annotate", required = true ) Document document,
            @ApiParam(value = "Transaction Id", required = false) @HeaderParam("X-Request-ID") String requestId
    ) throws ExecutionException {
        return Response.ok()
                .header(HttpHeaders.VARY, "Accept")
                .header(HttpHeaders.ETAG, "_87e52ce126126")
                .header(HttpHeaders.ALLOW, HttpMethod.POST)
                .header(HttpHeaders.ALLOW, HttpMethod.PUT)
                .entity(cesLiteralResponseFilterService.echoContentDocument(document))
                .build();
    }
}
