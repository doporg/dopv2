package com.clsaa.dop.server.testing.model.vo;

import com.clsaa.dop.server.testing.model.enums.StartType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TaskInfoVO implements Serializable {
    private String componentKey;
    private String status;
    private Date submittedAt;
    private StartType startType;
}
