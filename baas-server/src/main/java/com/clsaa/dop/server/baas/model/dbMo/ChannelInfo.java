package com.clsaa.dop.server.baas.model.dbMo;

import lombok.Data;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Data
public class ChannelInfo {
    private int id;
    private String ChannelName;
    private int NetId;
    private String PeerList;
}
