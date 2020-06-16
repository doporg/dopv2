package com.clsaa.dop.server.link.enums;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "操作类型")
public enum Operation {

    ADD,
    DEL,
    STOP,
    START
}
