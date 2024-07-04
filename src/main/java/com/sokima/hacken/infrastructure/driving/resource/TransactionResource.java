package com.sokima.hacken.infrastructure.driving.resource;

import com.sokima.hacken.domain.port.in.GetNodeTransactionInPort;
import com.sokima.hacken.infrastructure.driving.resource.in.TransactionsParam;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("transactions")
public class TransactionResource {
    @Inject
    GetNodeTransactionInPort getNodeTransactionInPort;

    @GET
    @Produces(APPLICATION_JSON)
    public Response getTransactions(@BeanParam Optional<TransactionsParam> transactionsParam) {
        return Response.ok(getNodeTransactionInPort.getNodeTransactionsByParam(
                transactionsParam.orElse(new TransactionsParam())
        )).build();
    }
}
