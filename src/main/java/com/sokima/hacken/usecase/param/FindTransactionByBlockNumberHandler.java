package com.sokima.hacken.usecase.param;

import com.sokima.hacken.domain.NodeTransaction;
import com.sokima.hacken.domain.port.out.FindTransactionOutPort;
import com.sokima.hacken.infrastructure.driving.resource.in.TransactionsParam;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Objects;
import java.util.Set;

@ApplicationScoped
public class FindTransactionByBlockNumberHandler extends AbstractTransactionParamsHandler implements TransactionParamsHandler {

    @Inject
    FindTransactionOutPort findTransactionOutPort;

    @Override
    protected Set<NodeTransaction> call(final TransactionsParam transactionsParam) {
        return findTransactionOutPort.findNodeTransactionsByBlockNumber(transactionsParam.getBlockNumber());
    }

    @Override
    public int ordered() {
        return 1;
    }

    @Override
    protected boolean isSupport(final TransactionsParam transactionsParam) {
        return Objects.nonNull(transactionsParam.getBlockNumber());
    }
}
