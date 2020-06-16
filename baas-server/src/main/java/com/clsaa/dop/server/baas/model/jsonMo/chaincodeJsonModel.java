package com.clsaa.dop.server.baas.model.jsonMo;

import lombok.Data;
/**
 * 注释写在这
 *
 * @author Monhey
 */
@Data
public class chaincodeJsonModel {
    private String name;
    private String version;
    private String git;
    private int channelId;
    private int networkId;
}

