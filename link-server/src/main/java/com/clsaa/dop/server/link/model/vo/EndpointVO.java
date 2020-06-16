package com.clsaa.dop.server.link.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EndpointVO {

    private String serviceName;

    private String ipv4;

    private String ipv6;

    private int port;
}
