package com.sokima.hacken.infrastructure.driven.starter;

import com.sokima.hacken.domain.port.out.ConnectEvmNodeOutPort;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

@ApplicationScoped
public class ConnectEvmNodeAdapter {

    @Inject
    Instance<ConnectEvmNodeOutPort> instance;

    public void onStart(@Observes StartupEvent startupEvent) {
        instance.forEach(ConnectEvmNodeOutPort::connect);
    }
}
