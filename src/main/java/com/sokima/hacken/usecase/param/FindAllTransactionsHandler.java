package com.sokima.hacken.usecase.param;

import com.sokima.hacken.domain.NodeTransaction;
import com.sokima.hacken.domain.port.out.FindTransactionOutPort;
import com.sokima.hacken.infrastructure.driving.resource.in.TransactionsParam;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Set;

@ApplicationScoped
public class FindAllTransactionsHandler extends AbstractTransactionParamsHandler implements TransactionParamsHandler {

    @Inject
    FindTransactionOutPort findTransactionOutPort;

    @Override
    protected Set<NodeTransaction> call(final TransactionsParam transactionsParam) {
        return findTransactionOutPort.findNodeTransactions();
    }

    @Override
    public int ordered() {
        return Integer.MAX_VALUE; // the last in chain
    }

    @Override
    protected boolean isSupport(final TransactionsParam transactionsParam) {
        return true; // the last in chain
    }
}
