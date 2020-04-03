package com.clsaa.dop.server.testing.model.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TaskMeasureBO implements Serializable {
    private String metric;
    private String value;
    private String component;
}
