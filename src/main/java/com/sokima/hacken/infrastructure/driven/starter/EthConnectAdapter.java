package com.sokima.hacken.infrastructure.driven.starter;

import com.sokima.hacken.domain.NodeTransaction;
import com.sokima.hacken.domain.port.out.ConnectEvmNodeOutPort;
import com.sokima.hacken.domain.port.out.SaveTransactionOutPort;
import com.sokima.hacken.usecase.EthNodePropertiesUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import jakarta.inject.Inject;

import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class EthConnectAdapter implements ConnectEvmNodeOutPort {

    private static final Logger log = LoggerFactory.getLogger(EthConnectAdapter.class);

    private final EthNodePropertiesUseCase ethProperties;

    private final SaveTransactionOutPort saveTransactionOutPort;

    private final Web3j web3j;

    @Inject
    public EthConnectAdapter(
            final EthNodePropertiesUseCase ethProperties,
            final SaveTransactionOutPort saveTransactionOutPort
    ) {
        this.ethProperties = ethProperties;
        this.saveTransactionOutPort = saveTransactionOutPort;
        this.web3j = Web3j.build(new HttpService(ethProperties.nodeUrl()));
    }

    @Override
    public void connect() {
        CompletableFuture.runAsync(() -> {
            log.info("Connecting to eth node: {}", ethProperties.nodeUrl());
            web3j.transactionFlowable()
                    .map(
                            tx -> new NodeTransaction(
                                    ethProperties.nodeType(),
                                    tx.getChainId(),
                                    tx.getBlockNumber(),
                                    tx.getTransactionIndex(),
                                    tx.getFrom(),
                                    tx.getTo(),
                                    tx.getGas(),
                                    tx.getGasPrice(),
                                    tx.getValue()
                            )
                    )
                    .doOnNext(tx -> log.info("Inbound eth tx: {}", tx))
                    .subscribe(saveTransactionOutPort::saveTx, ex -> {
                        if (ex != null) {
                            log.error("Error saving eth transaction", ex);
                            connect();
                            Thread.currentThread().interrupt();
                        }
                    });
        });
    }
}
