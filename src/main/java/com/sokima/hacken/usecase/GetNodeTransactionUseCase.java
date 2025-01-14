package com.sokima.hacken.usecase;

import com.sokima.hacken.domain.NodeTransaction;
import com.sokima.hacken.domain.port.in.GetNodeTransactionInPort;
import com.sokima.hacken.infrastructure.driving.resource.in.TransactionsParam;
import com.sokima.hacken.usecase.param.FindTransactionByToHandler;
import io.quarkus.arc.All;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import java.util.Set;

@ApplicationScoped
public class GetNodeTransactionUseCase implements GetNodeTransactionInPort {

    @Inject
    FindTransactionByToHandler headHandler;

    @Override
    @Timed(name = "usecase-time", unit = MetricUnits.MILLISECONDS)
    @Counted(name = "usecase-count")
    public Set<NodeTransaction> getNodeTransactionsByParam(final TransactionsParam transactionsParam) {
        return headHandler.extractTransactions(transactionsParam);
    }
}
