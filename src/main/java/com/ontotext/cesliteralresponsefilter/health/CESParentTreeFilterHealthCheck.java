package com.ontotext.cesliteralresponsefilter.health;

import com.codahale.metrics.health.HealthCheck;

/** **/
public class CESParentTreeFilterHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {

        // @TODO add some proper health checks
        return HealthCheck.Result.healthy();
    }
}
