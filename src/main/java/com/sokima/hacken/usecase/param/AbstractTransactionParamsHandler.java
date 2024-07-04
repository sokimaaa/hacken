package com.sokima.hacken.usecase.param;

import com.sokima.hacken.domain.NodeTransaction;
import com.sokima.hacken.infrastructure.driving.resource.in.TransactionsParam;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public abstract class AbstractTransactionParamsHandler implements TransactionParamsHandler {

    protected TransactionParamsHandler next;

    public static TransactionParamsHandler createChain(final List<AbstractTransactionParamsHandler> transactionParamsHandlers) {
        final var orderedList = transactionParamsHandlers.stream().sorted(Comparator.comparingInt(TransactionParamsHandler::ordered)).toList();
        for (int i = 0; i < orderedList.size() - 1; i++) {
            orderedList.get(i).setNext(orderedList.get(i + 1));
        }
        return orderedList.get(0);
    }

    private void setNext(final TransactionParamsHandler transactionParamsHandler) {
        this.next = transactionParamsHandler;
    }

    @Override
    public Set<NodeTransaction> extractTransactions(final TransactionsParam transactionsParam) {
        if (isSupport(transactionsParam)) {
            return this.call(transactionsParam);
        }
        return this.callNext(transactionsParam);
    }

    protected abstract Set<NodeTransaction> call(final TransactionsParam transactionsParam);

    protected abstract boolean isSupport(final TransactionsParam transactionsParam);

    private Set<NodeTransaction> callNext(final TransactionsParam transactionsParam) {
        return next.extractTransactions(transactionsParam);
    }
}
