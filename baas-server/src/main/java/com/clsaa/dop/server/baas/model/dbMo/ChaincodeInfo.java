package com.clsaa.dop.server.baas.model.dbMo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Data
@Getter
@Setter
public class ChaincodeInfo {
    private String id;
    private String ChaincodeName;
    private String ChaincodeVersion;
    private String git;
    private int ChannelId;
    private int NetId;
}