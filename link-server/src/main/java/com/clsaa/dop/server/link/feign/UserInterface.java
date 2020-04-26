package com.clsaa.dop.server.link.feign;


import com.clsaa.dop.server.link.config.FeignConfig;
import com.clsaa.dop.server.link.model.vo.UserV1;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "user-server", configuration = FeignConfig.class)
public interface UserInterface {

    @GetMapping("/v1/users/{id}")
    UserV1 findUserById(@PathVariable("id") Long id);
}
