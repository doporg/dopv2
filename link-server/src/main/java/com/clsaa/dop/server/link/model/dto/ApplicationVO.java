package com.clsaa.dop.server.link.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationVO {

    /**
     * 应用id
     */
    private Long id;

    /**
     * 应用拥有者
     */
    private Long ouser;

    /**
     * 应用拥有者
     */
    private String ouserName;

    /**
     * 应用名称
     */
    private String title;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 应用描述
     */
    private String description;
}
