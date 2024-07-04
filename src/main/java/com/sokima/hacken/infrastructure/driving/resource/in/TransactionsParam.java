package com.sokima.hacken.infrastructure.driving.resource.in;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
public class TransactionsParam {
    @QueryParam("to")
    private String to;
    @QueryParam("nodeType")
    private String nodeType;
    @QueryParam("blockNumber")
    private BigInteger blockNumber;
}
