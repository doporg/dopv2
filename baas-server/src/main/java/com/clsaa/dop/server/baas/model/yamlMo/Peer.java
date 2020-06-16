package com.clsaa.dop.server.baas.model.yamlMo;

import lombok.Data;

import java.util.List;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Data
public class Peer {
    private String peerName;
    private List<String> ports;
}