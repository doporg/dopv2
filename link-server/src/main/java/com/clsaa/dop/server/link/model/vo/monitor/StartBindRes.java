package com.clsaa.dop.server.link.model.vo.monitor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartBindRes {

    private String result;

    private String repeatTitle;

    private String repeatCuserName;

    public StartBindRes(String res) {
        this.result = res;
    }
}
