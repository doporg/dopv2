package com.clsaa.dop.server.example.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    //如果需要使用服务层对象，则在此声明一个私有对象再进行使用

    @ApiOperation(value = "接口名称", notes = "接口说明")
    @PostMapping("/v2/example")
    /*
     * 访问用 PostMapping, 相应的，创建数据或删除输入需要用其他注解（GerMapping / DeleteMapping）
     */
    public String hellWorld(
            /*
            *若有参数则需要进行说明
            @ApiParam(name,value,required,defaultValue)
            @RequestParam(value,required,defaultValue) <E> param ,
             */
    ) {
        /*
         * 函数方法体
         */
        return "helloWorld!";

    }
}
