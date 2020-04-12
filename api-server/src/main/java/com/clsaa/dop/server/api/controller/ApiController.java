package com.clsaa.dop.server.api.controller;

import com.clsaa.dop.server.api.module.vo.request.lifeCycle.CreateApiParams;
import com.clsaa.dop.server.api.module.vo.request.lifeCycle.ModifyApiParams;
import com.clsaa.dop.server.api.module.vo.response.ApiDetail;
import com.clsaa.dop.server.api.module.vo.response.ResponseResult;
import com.clsaa.dop.server.api.service.ApiService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

/**
 * Api应用启动类
 *
 * @author 郑博文
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/v2/api/lifeCycle")
@EnableAutoConfiguration
public class ApiController {
    private ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    //如果需要使用服务层对象，则在此声明一个私有对象再进行使用

    @ApiOperation(value = "接口名称", notes = "接口说明")
    @PostMapping("/HelloWorld")
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

    @ApiOperation(value = "创建api")
    @PostMapping()
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<String> createApi(@ApiParam(name = "创建api设置参数", required = true) @RequestBody CreateApiParams createApiParams){
        return apiService.createApi(createApiParams);
    }

    @ApiOperation(value = "上线api")
    @PutMapping("/online/{apiId}")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult onlineApi(@ApiParam(name = "api Id", required = true) @PathVariable("apiId") String apiId){
        return apiService.onlineApi(apiId);
    }

    @ApiOperation(value = "下线api")
    @PutMapping("/offline/{apiId}")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult offlineApi(@ApiParam(name = "api Id", required = true) @PathVariable("apiId") String apiId){
        return apiService.offlineApi(apiId);
    }

    @ApiOperation(value = "删除api")
    @DeleteMapping("/{apiId}")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult deleteApi(@ApiParam(name = "api Id", required = true) @PathVariable("apiId") String apiId){
        return apiService.deleteApi(apiId);
    }

    @ApiOperation(value = "修改api")
    @PatchMapping()
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult modifyApi(@ApiParam(name = "修改api设置参数", required = true) @RequestBody ModifyApiParams modifyApiParams){
        return apiService.modifyApi(modifyApiParams);
    }

    @ApiOperation(value = "查看api详情")
    @GetMapping("/{apiId}")
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<ApiDetail> getApiDetail(@ApiParam(name = "api Id", required = true) @PathVariable("apiId") String apiId){
        return apiService.getApi(apiId);
    }

    @ApiOperation(value = "查看api列表")
    @GetMapping()
    @ApiResponses({
            @ApiResponse(code = 400,message = "错误参数")
    })
    public ResponseResult<ApiDetail[]> getApi(){
        return apiService.getApiList();
    }
}
