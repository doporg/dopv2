package com.clsaa.dop.server.testing.model.bo;

import lombok.Data;


@Data
public class TaskMeasureBO {
    private String metric;
    private String value;
    private String component;
}
