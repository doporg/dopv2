package com.clsaa.dop.server.testing.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskMeasureVO implements Serializable {
    private String metric;
    private String value;
}
