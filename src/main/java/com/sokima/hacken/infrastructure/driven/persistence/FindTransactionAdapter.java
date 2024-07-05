package com.sokima.hacken.infrastructure.driven.persistence;

import com.sokima.hacken.domain.NodeTransaction;
import com.sokima.hacken.domain.port.out.FindTransactionOutPort;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigInteger;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class FindTransactionAdapter implements FindTransactionOutPort {

    private static final int LIMIT_START = 0;
    private static final int LIMIT_END = 500;


    @Override
    public Set<NodeTransaction> findNodeTransactions() {
        return TransactionEntity.<TransactionEntity>findAll()
                .range(LIMIT_START, LIMIT_END)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NodeTransaction> findNodeTransactionsByTo(final String to) {
        return TransactionEntity.findByTo(to)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NodeTransaction> findNodeTransactionsByNodeType(final String nodeType) {
        return TransactionEntity.findByNodeType(nodeType)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NodeTransaction> findNodeTransactionsByBlockNumber(final BigInteger blockNumber) {
        return TransactionEntity.findByBlockNumber(blockNumber.longValue())
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toSet());
    }

    private NodeTransaction mapToDomain(final TransactionEntity transactionEntity) {
        return new NodeTransaction(
                transactionEntity.nodeType,
                transactionEntity.chainId,
                transactionEntity.blockNumber,
                transactionEntity.transactionIndex,
                transactionEntity.from,
                transactionEntity.to,
                transactionEntity.gas,
                transactionEntity.gasPrice,
                transactionEntity.txValue
        );
    }
}
