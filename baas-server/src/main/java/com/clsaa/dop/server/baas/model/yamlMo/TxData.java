package com.clsaa.dop.server.baas.model.yamlMo;

import lombok.Data;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Data
public class TxData {
    private int blockNumber;
    private String time;
    private String chainCodeName;
    private String channelName;
    private String txId;
}