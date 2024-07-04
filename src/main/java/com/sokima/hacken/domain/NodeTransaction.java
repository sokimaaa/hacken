package com.sokima.hacken.domain;

import java.math.BigInteger;

public record NodeTransaction(
        String nodeType,
        Long chainId,
        BigInteger blockNumber,
        BigInteger transactionIndex,
        String from,
        String to,
        BigInteger gas,
        BigInteger gasPrice,
        BigInteger txValue
) {
}
