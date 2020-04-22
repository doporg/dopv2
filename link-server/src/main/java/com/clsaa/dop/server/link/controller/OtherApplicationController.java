package com.clsaa.dop.server.link.controller;

import com.clsaa.dop.server.link.config.HttpHeaders;
import com.clsaa.dop.server.link.feign.ProjectInterface;
import com.clsaa.dop.server.link.model.dto.ProjectVO;
import com.clsaa.dop.server.link.model.vo.UserV1;
import com.clsaa.rest.result.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
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
        }
        return result;
    }

    @GetMapping(value = "/project/members")
    public List<UserV1> getMembersByProjectId(@RequestParam(value = "user-id") Long loginUser,
                                              @RequestParam(value = "projectId") Long projectId,
                                              @RequestParam(value = "organizationId") Long organizationId) {
        System.out.println("loginUser: " + loginUser);
        System.out.println("projectId: " + projectId);
        System.out.println("organizationId: " + organizationId);
        List<UserV1> result = new ArrayList<>();
        /*
        {
     *         "id": 2,
     *         "name": "Jerry",
     *         "email": "552000264@qq.com",
     *         "avatarURL": "",
     *         "ctime": "2019-12-22T13:49:29.000Z",
     *         "mtime": "2019-12-22T13:49:29.000Z"
     *     }
         */
        result.add(new UserV1((long) 2,"Jerry", "552000264@qq.com","", null, null));
        result.add(new UserV1((long) 3,"Tom", "779722192@qq.com","", null, null));
        result.add(new UserV1((long) 4,"Rose", "298109291@qq.com","", null, null));
        return result;
//        return projectInterface.getMemberInProject(loginUser, projectId, organizationId);
    }
}
