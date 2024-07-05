package com.sokima.hacken.infrastructure.driving.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Path("ping")
public class HealthcheckResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(name = "healthcheck-count")
    @Timed(name = "healthcheck-time", unit = MetricUnits.MILLISECONDS)
    public String ping() {
        return "pong";
    }
}
