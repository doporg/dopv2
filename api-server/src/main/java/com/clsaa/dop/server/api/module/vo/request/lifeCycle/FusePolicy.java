package com.clsaa.dop.server.api.module.vo.request.lifeCycle;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "熔断策略")
public class FusePolicy {
    @ApiModelProperty(value = "enable",dataType = "boolean")
    private boolean enable;

    @ApiModelProperty(value = "fuseDetectionRing",dataType = "int")
    private Long fuseDetectionRing;

    @ApiModelProperty(value = "criticalFusingFailureRate",dataType = "int")
    private Long criticalFusingFailureRate;

    @ApiModelProperty(value = "fuseDuration",dataType = "int")
    private Long fuseDuration;

    @ApiModelProperty(value = "replyDetectionRingSize",dataType = "int")
    private Long replyDetectionRingSize;

    public FusePolicy(boolean enable, Long fuseDetectionRing, Long criticalFusingFailureRate, Long fuseDuration, Long replyDetectionRingSize) {
        this.enable = enable;
        this.fuseDetectionRing = fuseDetectionRing;
        this.criticalFusingFailureRate = criticalFusingFailureRate;
        this.fuseDuration = fuseDuration;
        this.replyDetectionRingSize = replyDetectionRingSize;
    }

    public FusePolicy() {
    }

    public boolean isEnable() {
        return enable;
    }

    public Long getFuseDetectionRing() {
        return fuseDetectionRing;
    }

    public Long getCriticalFusingFailureRate() {
        return criticalFusingFailureRate;
    }

    public Long getFuseDuration() {
        return fuseDuration;
    }

    public Long getReplyDetectionRingSize() {
        return replyDetectionRingSize;
    }
}
