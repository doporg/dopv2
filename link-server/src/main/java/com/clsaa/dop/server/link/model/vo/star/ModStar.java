package com.clsaa.dop.server.link.model.vo.star;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModStar {

    private long sid;

    private String newNote;
}
