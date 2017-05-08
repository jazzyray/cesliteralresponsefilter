package com.ontotext.cesliteralresponsefilter.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

/** **/
public class CESLiteralResponseFilterConfiguration extends Configuration {

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;
}
