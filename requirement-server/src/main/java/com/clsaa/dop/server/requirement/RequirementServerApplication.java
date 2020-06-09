package com.clsaa.dop.server.requirement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 应用启动类
 *
 * @author ZJH
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableFeignClients
@ComponentScan("com.clsaa")
public class RequirementServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RequirementServerApplication.class, args);
    }
}
