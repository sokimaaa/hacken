package com.sokima.hacken.domain.port.in;

import com.sokima.hacken.domain.NodeTransaction;
import com.sokima.hacken.infrastructure.driving.resource.in.TransactionsParam;

import java.util.Set;

public interface GetNodeTransactionInPort {
    Set<NodeTransaction> getNodeTransactionsByParam(final TransactionsParam transactionsParam);
}
