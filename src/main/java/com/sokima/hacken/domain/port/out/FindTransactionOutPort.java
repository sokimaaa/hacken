package com.sokima.hacken.domain.port.out;

import com.sokima.hacken.domain.NodeTransaction;

import java.math.BigInteger;
import java.util.Set;

public interface FindTransactionOutPort {
    Set<NodeTransaction> findNodeTransactions();

    Set<NodeTransaction> findNodeTransactionsByTo(final String to);

    Set<NodeTransaction> findNodeTransactionsByNodeType(final String nodeType);

    Set<NodeTransaction> findNodeTransactionsByBlockNumber(final BigInteger blockNumber);
}
