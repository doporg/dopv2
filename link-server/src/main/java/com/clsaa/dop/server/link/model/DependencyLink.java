package com.clsaa.dop.server.link.model;

import lombok.Data;

@Data
public class DependencyLink {

    private String parent;

    private String child;

    private long callCount;

    private long errorCount;
}
