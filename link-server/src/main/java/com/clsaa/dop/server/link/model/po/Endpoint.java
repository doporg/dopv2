package com.clsaa.dop.server.link.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The network context of a node in the service graph
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Endpoint {

    @Id
    private String eid; // == spanId+ r/l 代表span的remote和local

    private String serviceName;

    private String ipv4;

    private String ipv6;

    private int port;
}
