package com.clsaa.dop.server.testing.model.bo;

import com.clsaa.dop.server.testing.model.enums.IssueSeverity;
import com.clsaa.dop.server.testing.model.enums.IssueType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Issue implements Serializable{
        private String key ;
        private String rule;
        private IssueSeverity severity;
        private String component;
        private String project;
        private int line;
        private String message;
        private String effort;
        private String author;
        private Date creationDate;
        private Date updateDate;
        private IssueType type;


}
