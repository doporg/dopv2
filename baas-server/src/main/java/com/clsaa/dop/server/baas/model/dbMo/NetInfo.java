package com.clsaa.dop.server.baas.model.dbMo;

import lombok.Data;

import java.util.Date;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Data
public class NetInfo {
    private int id;
    private String NetName;
    private String Description;
    private String CreateTime;
    private int Status;
}
