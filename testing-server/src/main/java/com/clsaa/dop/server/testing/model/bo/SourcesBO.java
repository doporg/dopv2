package com.clsaa.dop.server.testing.model.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SourcesBO implements Serializable {
    private List<Source> sources;


}
