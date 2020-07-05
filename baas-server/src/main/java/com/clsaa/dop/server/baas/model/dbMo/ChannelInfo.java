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
public class ChannelInfo {
    private int id;
    private String ChannelName;
    private int NetId;
    private String PeerList;
}
