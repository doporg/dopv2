package com.clsaa.dop.server.testing.model.dto;

import com.clsaa.dop.server.testing.util.LanguageType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *  CI代码扫描数据传输层对象
 * @author Vettel
 */
@Getter
@Setter
public class RepoScanDTO implements Serializable {

    @JsonProperty("code_path")
    private String codePath;

    @JsonProperty("project_name")
    private String projectName;

    @JsonProperty("code_user")
    private String codeUser;

    @JsonProperty("code_pwd")
    private String codePwd;

    @JsonProperty("language_type")
    private LanguageType type;
}
