package com.clsaa.dop.server.link.model;

import lombok.Data;

/**
 * The network context of a node in the service graph
 */
@Data
public class Endpoint {


    private String serviceName;

    private String ipv4;

    private String ipv6;

    private int port;
}
