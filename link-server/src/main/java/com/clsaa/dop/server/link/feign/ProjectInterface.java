package com.clsaa.dop.server.link.feign;

import com.clsaa.dop.server.link.config.FeignConfig;
import com.clsaa.dop.server.link.config.HttpHeaders;
import com.clsaa.dop.server.link.model.dto.ApplicationVO;
import com.clsaa.dop.server.link.model.dto.ProjectVO;
import com.clsaa.dop.server.link.model.vo.UserV1;
import com.clsaa.rest.result.Pagination;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value = "application-server", configuration = FeignConfig.class)
public interface ProjectInterface {

    @GetMapping("/pagedapp")
    Pagination<ApplicationVO> findApplicationByProjectId(
            @RequestHeader(HttpHeaders.X_LOGIN_USER) Long loginUser,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "projectId") Long projectId,
            @RequestParam(value = "queryKey", defaultValue = "") String queryKey);

    @GetMapping("/paged-project")
    Pagination<ProjectVO> findProjectOrderByCtimeWithPage(
            @RequestHeader(HttpHeaders.X_LOGIN_USER) Long loginUser,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "includeFinished", defaultValue = "false") Boolean includeFinished,
            @RequestParam(value = "queryKey", defaultValue = "") String queryKey);

    /*
     * [
     *     {
     *         "id": 2,
     *         "name": "Jerry",
     *         "email": "552000264@qq.com",
     *         "avatarURL": "",
     *         "ctime": "2019-12-22T13:49:29.000Z",
     *         "mtime": "2019-12-22T13:49:29.000Z"
     *     },
     *     {
     *         "id": 3,
     *         "name": "Tom",
     *         "email": "123456789@qq.com",
     *         "avatarURL": "",
     *         "ctime": "2019-12-22T13:49:29.000Z",
     *         "mtime": "2019-12-22T13:49:29.000Z"
     *     }
     * ]
     */
    @GetMapping(value = "/project/{projectId}/members")
    List<UserV1> getMemberInProject(@RequestHeader(HttpHeaders.X_LOGIN_USER) Long loginUser,
                                    @PathVariable(value = "projectId") Long projectId,
                                    @RequestParam(value = "organizationId") Long organizationId);

}
