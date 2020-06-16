package com.clsaa.dop.server.testing.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CIScanBO implements Serializable {
    private String projectKey;
    private String codePath;
    private String version;
    private String branch;
}
