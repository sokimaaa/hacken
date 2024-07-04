package com.sokima.hacken.usecase;

import com.sokima.hacken.domain.port.out.NodePropertiesOutPort;
import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "eth-node.properties")
public interface EthNodePropertiesUseCase extends NodePropertiesOutPort {
}
