package com.sokima.hacken.infrastructure.driving.resource;

import com.sokima.hacken.domain.NodeTransaction;
import com.sokima.hacken.domain.port.in.GetNodeTransactionInPort;
import com.sokima.hacken.infrastructure.driving.resource.in.TransactionsParam;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.Optional;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("transactions")
public class TransactionResource {
    @Inject
    GetNodeTransactionInPort getNodeTransactionInPort;

    @GET
    @Produces(APPLICATION_JSON)
    @Timed(name = "tx-endpoint-time", unit = MetricUnits.MILLISECONDS)
    @Counted(name = "tx-endpoint-count")
    @Operation(
            summary = "get transactions endpoint",
            description = "allows to get any transactions records or filtered by tx params."
    )
    @Parameter(
            allowEmptyValue = true,
            name = "transactionParam",
            description = "Optional object that represents transaction params to apply",
            required = false
    )
    @APIResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(schema = @Schema(implementation = NodeTransaction.class))
    )
    public Response getTransactions(@BeanParam Optional<TransactionsParam> transactionsParam) {
        final var txParam = transactionsParam.orElse(new TransactionsParam());
        final var result = getNodeTransactionInPort.getNodeTransactionsByParam(txParam);
        return Response.ok(result).build();
    }
}
