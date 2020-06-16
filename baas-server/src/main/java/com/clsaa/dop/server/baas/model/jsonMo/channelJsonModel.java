package com.clsaa.dop.server.baas.model.jsonMo;

import lombok.Data;

import java.util.List;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Data
public class channelJsonModel {
    private String name;
    private String networkId;
    private List<String> peers;
}