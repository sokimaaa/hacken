package com.sokima.hacken.usecase.param;

import com.sokima.hacken.domain.NodeTransaction;
import com.sokima.hacken.infrastructure.driving.resource.in.TransactionsParam;

import java.util.Set;

public interface TransactionParamsHandler {
    Set<NodeTransaction> extractTransactions(final TransactionsParam transactionsParam);

    int ordered();
}
