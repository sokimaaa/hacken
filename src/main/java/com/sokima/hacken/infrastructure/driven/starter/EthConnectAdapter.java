package com.sokima.hacken.infrastructure.driven.starter;

import com.sokima.hacken.domain.NodeTransaction;
import com.sokima.hacken.domain.port.out.ConnectEvmNodeOutPort;
import com.sokima.hacken.domain.port.out.SaveTransactionOutPort;
import com.sokima.hacken.usecase.EthNodePropertiesUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@ApplicationScoped
public class EthConnectAdapter implements ConnectEvmNodeOutPort {

    private static final Logger log = LoggerFactory.getLogger(EthConnectAdapter.class);

    private static final long RETRY_NUMBER = 10L;

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
                .retry(RETRY_NUMBER)
                .subscribe(saveTransactionOutPort::saveTx);
    }
}
