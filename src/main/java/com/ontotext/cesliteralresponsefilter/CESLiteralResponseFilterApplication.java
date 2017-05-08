package com.ontotext.cesliteralresponsefilter;

import com.ontotext.cesliteralresponsefilter.config.CESLiteralResponseFilterConfiguration;
import com.ontotext.cesliteralresponsefilter.filter.CESLiteralResponseFilter;
import com.ontotext.cesliteralresponsefilter.filter.CESLiteralResponseFilterFilterService;
import com.ontotext.cesliteralresponsefilter.health.CESParentTreeFilterHealthCheck;
import com.ontotext.cesliteralresponsefilter.resource.CESLiteralResponseFilterResource;
import com.ontotext.cesliteralresponsefilter.service.CESLiteralResponseFilterService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

/** **/
public class CESLiteralResponseFilterApplication extends Application<CESLiteralResponseFilterConfiguration> {

    CESLiteralResponseFilterService cesLiteralResponseFilterService;

    @Override
    public void run(CESLiteralResponseFilterConfiguration configuration, Environment environment) throws Exception {
        this.cesLiteralResponseFilterService = new CESLiteralResponseFilterService();

        final CESLiteralResponseFilterResource resource = new CESLiteralResponseFilterResource(this.cesLiteralResponseFilterService);

        final CESLiteralResponseFilterFilterService cesLiteralResponseFilterFilterService = new CESLiteralResponseFilterFilterService();
        final CESLiteralResponseFilter filter = new CESLiteralResponseFilter(cesLiteralResponseFilterFilterService);

        final CESParentTreeFilterHealthCheck healthCheck = new CESParentTreeFilterHealthCheck();
        environment.healthChecks().register("cesparenttreefilter", healthCheck);

        environment.jersey().register(resource);
        environment.jersey().register(filter);
    }

    @Override
    public void initialize(Bootstrap<CESLiteralResponseFilterConfiguration> bootstrap) {

        bootstrap.addBundle(new SwaggerBundle<CESLiteralResponseFilterConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(CESLiteralResponseFilterConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    public static void main(String[] args) throws Exception {
        new CESLiteralResponseFilterApplication().run(args);
    }
}
