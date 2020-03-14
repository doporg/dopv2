package com.clsaa.dop.server.testing.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RepoScanDTO implements Serializable {

    @JsonProperty("code_path")
    private String codePath;

    @JsonProperty("project_name")
    private String projectName;

    @JsonProperty("sonar_token")
    private String sonarToken;

    @JsonProperty("code_user")
    private String codeUser;

    @JsonProperty("code_pwd")
    private String codePwd;
}
