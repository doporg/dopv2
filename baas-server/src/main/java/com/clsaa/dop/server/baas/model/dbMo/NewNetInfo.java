package com.clsaa.dop.server.baas.model.dbMo;

import lombok.Data;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Data
public class NewNetInfo {
    private int id;
    private String namespace;
    private String ordererList;
    private String orgList;
    private String consensus;
    private String tls;
    private String createtime;
    private int status;
    public String toString(){
        return "id:"+id+"\nnamespace:"+namespace+"\nordererList:"+ordererList+"\norgList"+orgList+"\nconsensus:"+consensus+"\ntls:"+tls+"\ncreateTime:"+createtime+"\nstatus:"+status;
    }
}