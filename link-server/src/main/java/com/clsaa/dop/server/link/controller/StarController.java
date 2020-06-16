package com.clsaa.dop.server.link.controller;

import com.clsaa.dop.server.link.model.po.StarTrace;
import com.clsaa.dop.server.link.model.vo.star.AddOrDelStar;
import com.clsaa.dop.server.link.model.vo.star.ModStar;
import com.clsaa.dop.server.link.service.StarTraceService;
import com.clsaa.rest.result.Pagination;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/v2/link/star")
//@EnableAutoConfiguration
public class StarController {

    @Autowired
    private StarTraceService service;

    @ApiOperation(value = "查看收藏列表")
    @GetMapping()
    public Pagination<StarTrace> getStarList(
            @ApiParam(value = "用户Id", required = true)
            @RequestParam(value = "userId")Long userId,
            @ApiParam(value = "页号", defaultValue = "1")
            @RequestParam(value = "pageNo", required = false, defaultValue = "1")Integer pageNo,
            @ApiParam(value = "分页大小", defaultValue = "10")
            @RequestParam(value = "pageSize", required = false, defaultValue = "10")Integer pageSize,
            @ApiParam(value = "查找关键字", defaultValue = "")
            @RequestParam(value = "keyword", required = false, defaultValue = "")String keyword) {
        return service.getStars(userId, pageNo, pageSize, keyword);
    }

    @ApiOperation(value = "新增或取消收藏")
    @PostMapping()
    public void starTrace(
            @ApiParam(name = "新增或取消收藏参数", required = true)
            @RequestBody AddOrDelStar addOrDelStar) {
        System.out.println(addOrDelStar.toString());
        service.star(addOrDelStar);
    }

    @ApiOperation(value = "判断是否已收藏")
    @GetMapping(value = "/exist")
    public boolean judgeHasStar(
            @ApiParam(value = "用户Id", required = true)@RequestParam("userId")Long userId,
            @ApiParam(value = "链路Id", required = true)@RequestParam("traceId")String traceId) {
        System.out.println("exist: " + userId + ", "+ traceId);
        return service.hasStar(userId, traceId);
    }

    @ApiOperation(value = "编辑收藏备注")
    @PostMapping(value = "/note")
    public void updateNote(@ApiParam(value = "编辑参数", required = true)@RequestBody ModStar modStar) {
        System.out.println("modify: " + modStar.toString());
        service.updateStar(modStar);
    }
}
