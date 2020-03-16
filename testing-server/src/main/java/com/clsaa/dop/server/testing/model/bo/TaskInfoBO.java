package com.clsaa.dop.server.testing.model.bo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskInfoBO {
    private String componentKey;
    private String status;
    private String submittedAt;
    private String createWay;
}
