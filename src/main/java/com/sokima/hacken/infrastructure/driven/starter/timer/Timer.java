package com.sokima.hacken.infrastructure.driven.starter.timer;

import java.time.Duration;
import java.time.Instant;

/**
 * Workaround to provide reliability in case of:
 * WARN  [org.web.pro.cor.fil.Filter] (pool-9-thread-1) The filter has not been found. Filter id: 14621507953634074608218513988515298149
 * ERROR [org.web.pro.cor.fil.Filter] (pool-9-thread-1) Error sending request: org.web3j.protocol.core.filters.FilterException: Error sending request
 *         at org.web3j.protocol.core.filters.Filter.throwException(Filter.java:194)
 *         at org.web3j.protocol.core.filters.Filter.run(Filter.java:104)
 *         at org.web3j.protocol.core.filters.Filter.reinstallFilter(Filter.java:155)
 *         at org.web3j.protocol.core.filters.Filter.pollFilter(Filter.java:137)
 *         at org.web3j.protocol.core.filters.Filter.lambda$run$0(Filter.java:92)
 *         at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:539)
 *         at java.base/java.util.concurrent.FutureTask.runAndReset(FutureTask.java:305)
 *         at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:305)
 *         at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
 *         at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
 *         at java.base/java.lang.Thread.run(Thread.java:840)
 * Caused by: java.io.InterruptedIOException: interrupted
 */
public final class Timer {

    private Instant lastUpdateTime;

    private Timer(final Instant lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public static Timer start() {
        return new Timer(Instant.now());
    }

    public void updateTime() {
        this.lastUpdateTime = Instant.now();
    }

    public boolean isTimePassed(final long minutes) {
        final var timePassedInMinutes = Duration.between(lastUpdateTime, Instant.now()).toMinutes();
        return timePassedInMinutes > minutes;
    }
}
