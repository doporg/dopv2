package com.clsaa.dop.server.link.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
public class StarTrace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sid;

    private long userId;

    private String traceId;

    private Date date;

    private String note;
}
