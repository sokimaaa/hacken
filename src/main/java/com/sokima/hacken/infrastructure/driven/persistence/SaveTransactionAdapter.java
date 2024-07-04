package com.sokima.hacken.infrastructure.driven.persistence;

import com.sokima.hacken.domain.NodeTransaction;
import com.sokima.hacken.domain.port.out.SaveTransactionOutPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SaveTransactionAdapter implements SaveTransactionOutPort {
    @Override
    @Transactional
    public NodeTransaction saveTx(final NodeTransaction transaction) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.transactionIndex = transaction.transactionIndex();
        transactionEntity.txValue = transaction.txValue();
        transactionEntity.blockNumber = transaction.blockNumber();
        transactionEntity.gas = transaction.gas();
        transactionEntity.gasPrice = transaction.gasPrice();
        transactionEntity.to = transaction.to();
        transactionEntity.from = transaction.from();
        transactionEntity.nodeType = transaction.nodeType();
        transactionEntity.chainId = transaction.chainId();
        transactionEntity.persist();
        return transaction;
    }
}
