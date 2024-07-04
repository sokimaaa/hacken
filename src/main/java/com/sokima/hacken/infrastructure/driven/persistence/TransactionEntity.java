package com.sokima.hacken.infrastructure.driven.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.math.BigInteger;
import java.util.List;

@Entity
public class TransactionEntity extends PanacheEntity {
    public String nodeType;
    public Long chainId;
    public BigInteger blockNumber;
    public BigInteger transactionIndex;
    @Column(name = "txFrom") public String from;
    @Column(name = "txTo") public String to;
    public BigInteger gas;
    public BigInteger gasPrice;
    public BigInteger txValue;


    public static List<TransactionEntity> findByNodeType(final String nodeType) {
        return list("nodeType", nodeType);
    }

    public static List<TransactionEntity> findByBlockNumber(final Long blockNumber) {
        return list("blockNumber", blockNumber);
    }

    public static List<TransactionEntity> findByTo(final String to) {
        return list("txTo", to);
    }
}
