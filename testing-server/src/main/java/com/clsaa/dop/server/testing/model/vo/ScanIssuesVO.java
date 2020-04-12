package com.clsaa.dop.server.testing.model.vo;

import com.clsaa.dop.server.testing.model.bo.Issue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ScanIssuesVO implements Serializable {
    private int total;
    private int effortTotal;
    private List<Issue> issues;
}
