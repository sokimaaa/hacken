package com.sokima.hacken.infrastructure.driven.starter;

import com.sokima.hacken.domain.NodeTransaction;
import com.sokima.hacken.domain.port.out.ConnectEvmNodeOutPort;
import com.sokima.hacken.domain.port.out.SaveTransactionOutPort;
import com.sokima.hacken.infrastructure.driven.starter.timer.Timer;
import com.sokima.hacken.usecase.EthNodePropertiesUseCase;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.util.Objects;

@ApplicationScoped
public class EthConnectAdapter implements ConnectEvmNodeOutPort {

    private static final Logger log = LoggerFactory.getLogger(EthConnectAdapter.class);

    private static final long RETRY_NUMBER = 10L;
    private static final long REFRESH_RATE_MINUTES = 1L;

    private final EthNodePropertiesUseCase ethProperties;

    private final SaveTransactionOutPort saveTransactionOutPort;

    private final HttpService httpService;

    private Web3j web3j;

    private final Timer timer;

    @Inject
    public EthConnectAdapter(
            final EthNodePropertiesUseCase ethProperties,
            final SaveTransactionOutPort saveTransactionOutPort
    ) {
        this.ethProperties = ethProperties;
        this.saveTransactionOutPort = saveTransactionOutPort;
        this.httpService = new HttpService(ethProperties.nodeUrl());
        this.timer = Timer.start();
    }

    @Scheduled(every = "60s")
    void checkConnectionState() {
        if (Objects.nonNull(timer) && timer.isTimePassed(REFRESH_RATE_MINUTES)) {
            log.info("{} minutes passed without updates, initiated refreshing of {}.", REFRESH_RATE_MINUTES, getClass().getSimpleName());
            reconnect();
        }
    }

    @Override
    public void connect() {
        timer.updateTime();
        web3j = Web3j.build(httpService);
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
                .doOnNext(tx -> timer.updateTime())
                .retry(RETRY_NUMBER)
                .subscribe(saveTransactionOutPort::saveTx, ex -> {
                    log.warn("Error while saving eth tx: {}", ex.getMessage());
                    reconnect();
                });
    }

    private void reconnect() {
        web3j.shutdown();
        connect();
    }
}
