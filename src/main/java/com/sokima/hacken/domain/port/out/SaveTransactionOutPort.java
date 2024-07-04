package com.sokima.hacken.domain.port.out;

import com.sokima.hacken.domain.NodeTransaction;

public interface SaveTransactionOutPort {
    NodeTransaction saveTx(final NodeTransaction transaction);
}
