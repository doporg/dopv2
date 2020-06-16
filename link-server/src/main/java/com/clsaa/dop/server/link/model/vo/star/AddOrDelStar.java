package com.clsaa.dop.server.link.model.vo.star;

import com.clsaa.dop.server.link.enums.Operation;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "新增或取消收藏参数")
public class AddOrDelStar {

    private long userId;

    private String traceId;

    private String note;
    
    private Operation operation;
}
