package com.clsaa.dop.server.testing.model.vo;

import com.clsaa.dop.server.testing.model.bo.Source;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SourcesVO implements Serializable {
    private List<Source> sources;

}
