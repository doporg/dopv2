package com.clsaa.dop.server.link.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectVO {

    /**
     * 项目Id
     */
    private Long id;


    /**
     * 项目名称
     */
    private String title;

    /**
     * 创建人
     */
    private Long cuser;

    /**
     * 创建人
     */
    private String cuserName;

    /**
     * 创建日期
     */
    private LocalDateTime ctime;

    /**
     * 项目描述
     */
    private String description;
}
