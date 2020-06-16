package com.clsaa.dop.server.api.module.po;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class LimitPolicy {
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int second;

    @Column(nullable = false)
    private int minute;

    @Column(nullable = false)
    private int hour;

    @Column(nullable = false)
    private int day;


    @OneToMany(mappedBy = "limitPolicy",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Service> services;

    public LimitPolicy(String name, String description, int second, int minute, int hour, int day) {
        this.name = name;
        this.description = description;
        this.second = second;
        this.minute = minute;
        this.hour = hour;
        this.day = day;
    }
}
