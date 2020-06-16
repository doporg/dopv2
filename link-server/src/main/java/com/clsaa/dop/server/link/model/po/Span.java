package com.clsaa.dop.server.link.model.po;

import com.clsaa.dop.server.link.enums.Kind;
import com.clsaa.dop.server.link.model.po.Annotation;
import com.clsaa.dop.server.link.model.po.Endpoint;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Span {

    /**
     * id: "352bff9a74ca9ad2"
     * traceId: "5af7183fb1d4cf5f"
     * parentId: "6b221d5bc9e6496c"
     * name: "get /api"
     * timestamp: 1556604172355737
     * duration: 1431
     * kind: "SERVER"
     * remoteEndpointVO:
     *   serviceName: "backend"
     *   ipv4: "192.168.99.1"
     *   port: 3306
     * remoteEndpoint:
     *   ipv4: "172.19.0.2"
     *   port: 58648
     * tags:
     *   http.method: "GET"
     *   http.path: "/api"
     */

    private String traceId;//necessary

    private String name;

    private String parentId;

    @Id
    private String id;//spanId necessary

    @Enumerated(EnumType.STRING)
    private Kind kind;

    private long timestamp;

    private long duration; // zero means null, saving 2 object references

    private boolean debug;

    private boolean shared;

    @OneToOne
    private Endpoint localEndpoint;

    @OneToOne
    private Endpoint remoteEndpoint;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Annotation> annotations;

//    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "tag_id"))
//    @MapKeyColumn(name = "tag_key", columnDefinition = "varchar(100) default 'LTD'")
//    @Column(name = "tag_value")
//    @ElementCollection
    @Type(type = "json")
    @Column(columnDefinition = "json" )
    private Map<String, String> tags;
}
