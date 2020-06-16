package com.clsaa.dop.server.testing.model.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Source implements Serializable{
        private int line;
        private String code;
        private String scmRevision;
        private String scmAuthor;
        private Date scmDate;
}
