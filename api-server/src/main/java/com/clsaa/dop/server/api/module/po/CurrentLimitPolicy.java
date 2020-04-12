package com.clsaa.dop.server.api.module.po;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class CurrentLimitPolicy {
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private String circle;

    @Column(nullable = false)
    private int time;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "service_id")
    private Service service;

    public CurrentLimitPolicy(String name, String circle, int time, Service service) {
        this.name = name;
        this.circle = circle;
        this.time = time;
        this.service = service;
    }
}
