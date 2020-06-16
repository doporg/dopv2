package com.clsaa.dop.server.testing.model.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskMeasuresBO implements Serializable {
    private List<TaskMeasureBO> measures;
}
