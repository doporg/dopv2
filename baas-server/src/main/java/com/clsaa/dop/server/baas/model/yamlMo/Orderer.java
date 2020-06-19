package com.clsaa.dop.server.baas.model.yamlMo;

import lombok.Data;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Data
public class Orderer {
    private String orderName;
    private String orderport;
    public String toString(){
        return this.getOrderName()+":"+this.getOrderport();
    }
}