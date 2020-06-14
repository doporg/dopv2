package com.clsaa.dop.server.baas.model.dbMo;

import lombok.Data;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Data
public class ChaincodeInfo {
    private String id;
    private String ChaincodeName;
    private String ChaincodeVersion;
    private String git;
    private int ChannelId;
    private int NetId;
}