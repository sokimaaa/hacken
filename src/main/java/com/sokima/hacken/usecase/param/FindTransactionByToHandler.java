package com.sokima.hacken.usecase.param;

import com.sokima.hacken.domain.NodeTransaction;
import com.sokima.hacken.domain.port.out.FindTransactionOutPort;
import com.sokima.hacken.infrastructure.driving.resource.in.TransactionsParam;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.ToString;

import java.util.Objects;
import java.util.Set;

@ApplicationScoped
public class FindTransactionByToHandler implements TransactionParamsHandler {

    @Inject
    FindTransactionOutPort findTransactionOutPort;

    @Inject
    FindTransactionByNodeTypeHandler next;

    @Override
    public Set<NodeTransaction> extractTransactions(final TransactionsParam transactionsParam) {
        if (Objects.nonNull(transactionsParam.getTo())) {
            return findTransactionOutPort.findNodeTransactionsByTo(transactionsParam.getTo());
        }
        return next.extractTransactions(transactionsParam);
    }
}
