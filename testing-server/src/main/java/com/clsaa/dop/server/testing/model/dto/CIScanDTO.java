package com.clsaa.dop.server.testing.model.dto;


import com.clsaa.dop.server.testing.util.LanguageType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CIScanDTO implements Serializable {
    @JsonProperty("code_path")
    private String codePath;

    private String branch;

    @JsonProperty("code_user")
    private String codeUser;

    @JsonProperty("code_pwd")
    private String codePwd;

    @JsonProperty("language_type")
    private LanguageType type;
}
