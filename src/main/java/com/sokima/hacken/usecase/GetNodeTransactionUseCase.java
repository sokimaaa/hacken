package com.sokima.hacken.usecase;

import com.sokima.hacken.domain.NodeTransaction;
import com.sokima.hacken.domain.port.in.GetNodeTransactionInPort;
import com.sokima.hacken.infrastructure.driving.resource.in.TransactionsParam;
import com.sokima.hacken.usecase.param.AbstractTransactionParamsHandler;
import com.sokima.hacken.usecase.param.TransactionParamsHandler;
import io.quarkus.arc.All;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class GetNodeTransactionUseCase implements GetNodeTransactionInPort {

    private final TransactionParamsHandler headHandler;

    @Inject
    public GetNodeTransactionUseCase(@All final List<AbstractTransactionParamsHandler> handlers) {
        this.headHandler = AbstractTransactionParamsHandler.createChain(handlers);
    }

    @Override
    public Set<NodeTransaction> getNodeTransactionsByParam(final TransactionsParam transactionsParam) {
        return headHandler.extractTransactions(transactionsParam);
    }
}
