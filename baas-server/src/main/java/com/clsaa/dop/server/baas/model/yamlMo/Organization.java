package com.clsaa.dop.server.baas.model.yamlMo;

import lombok.Data;

import java.util.List;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Data
public class Organization {
    private String orgName;
    private List<Peer> peers;
}
