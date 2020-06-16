package com.clsaa.dop.server.baas.model.jsonMo;

import com.clsaa.dop.server.baas.model.yamlMo.Orderer;
import com.clsaa.dop.server.baas.model.yamlMo.Organization;
import lombok.Data;

import java.util.List;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Data
public class jsonModel {
    private String name;
    private List<Orderer> order;
    private List<Organization> org;
    private String consensus;
    private String tls;
}