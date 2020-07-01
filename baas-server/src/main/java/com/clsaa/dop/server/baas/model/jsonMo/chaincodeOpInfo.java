package com.clsaa.dop.server.baas.model.jsonMo;

import lombok.Data;

import java.util.List;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Data
public class chaincodeOpInfo {
    private int id;
    private String operationName;
    private String operationType;
    private List<String> operationParameter;
}
