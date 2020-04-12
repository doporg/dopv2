package com.clsaa.dop.server.testing.model.vo;

import com.clsaa.dop.server.testing.model.bo.TaskMeasureBO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskMeasuresVO implements Serializable {
    private List<TaskMeasureBO> measures;
}
