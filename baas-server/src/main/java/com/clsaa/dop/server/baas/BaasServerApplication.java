package com.clsaa.dop.server.baas;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 应用启动类
 *
 * @author joyren,lzy
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableFeignClients
@ComponentScan("com.clsaa")
@MapperScan(value = {"com.baasexample.demo.Mapper"})
public class BaasServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaasServerApplication.class, args);
    }
}
