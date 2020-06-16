package com.clsaa.dop.server.baas.model.yamlMo;

import lombok.Data;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Data
public class BlockData {
    private String DataHash;
    private int BlockNum;
    private String PreHash;
    private String TimeStamp;
}
