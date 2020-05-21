package com.clsaa.dop.server.baas.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 示例应用启动类
 *
 * @author 李质颖
 */
@CrossOrigin
@RestController
@EnableAutoConfiguration
public class HelloWorldController {
    @ApiOperation(value = "接口名称", notes = "接口说明")
    @GetMapping("/v2/baas/hello")
    public String hellWorld() {
//        V1Pod pod =
//                new V1PodBuilder()
//                        .withNewMetadata()
//                        .withName("apod")
//                        .endMetadata()
//                        .withNewSpec()
//                        .addNewContainer()
//                        .withName("www")
//                        .withImage("nginx")
//                        .withNewResources()
//                        .withLimits(new HashMap<>())
//                        .endResources()
//                        .endContainer()
//                        .endSpec()
//                        .build();
//        System.out.println(Yaml.dump(pod));
//
//        V1Service svc =
//                new V1ServiceBuilder()
//                        .withNewMetadata()
//                        .withName("aservice")
//                        .endMetadata()
//                        .withNewSpec()
//                        .withSessionAffinity("ClientIP")
//                        .withType("NodePort")
//                        .addNewPort()
//                        .withProtocol("TCP")
//                        .withName("client")
//                        .withPort(8008)
//                        .withNodePort(8080)
//                        .withTargetPort(new IntOrString(8080))
//                        .endPort()
//                        .endSpec()
//                        .build();
//        System.out.println(Yaml.dump(svc));

        return "helloWorld!";

    }
}
