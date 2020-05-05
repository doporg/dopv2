package com.clsaa.dop.server.testing.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CIScanVO implements Serializable {
    private String projectKey;
    private String codePath;
    private String version;
    private String branch;
}
