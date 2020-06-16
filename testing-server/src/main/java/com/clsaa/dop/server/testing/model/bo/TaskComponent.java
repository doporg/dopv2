package com.clsaa.dop.server.testing.model.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TaskComponent implements Serializable {
    private String componentKey;
    private String status;
    private Date submittedAt;
}
