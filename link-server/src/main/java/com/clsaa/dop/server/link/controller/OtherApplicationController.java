package com.clsaa.dop.server.link.controller;

import com.clsaa.dop.server.link.config.HttpHeaders;
import com.clsaa.dop.server.link.feign.ProjectInterface;
import com.clsaa.dop.server.link.model.dto.ProjectVO;
import com.clsaa.rest.result.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtherApplicationController {

    @Autowired
    private ProjectInterface projectInterface;

//    @GetMapping(value = "/getProjectList")
    public Pagination<ProjectVO> getProjectList(@RequestHeader(HttpHeaders.X_LOGIN_USER) Long loginUser,
                                                @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                @RequestParam(value = "includeFinished", defaultValue = "true") Boolean includeFinished,
                                                @RequestParam(value = "queryKey", defaultValue = "") String queryKey) {
        System.out.println("user: "+loginUser);
        System.out.println("pageNo: "+ pageNo);
        System.out.println("pageSize: " + pageSize);
        System.out.println("includeFinished: " + includeFinished);
        System.out.println("queryKey: " + queryKey);
        Pagination<ProjectVO> result =  projectInterface.findProjectOrderByCtimeWithPage(loginUser,pageNo, pageSize, includeFinished,queryKey);
        for (ProjectVO projectVO : result.getPageList()) {
            System.out.println(projectVO.toString());
        };
        return result;
    }

}
