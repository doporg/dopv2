package com.clsaa.dop.server.testing.controller;


import com.clsaa.dop.server.testing.config.BizCodes;
import com.clsaa.dop.server.testing.config.HttpHeaders;
import com.clsaa.dop.server.testing.model.dto.RepoScanDTO;
import com.clsaa.dop.server.testing.service.RepoScanService;
import com.clsaa.rest.result.bizassert.BizAssert;
import com.clsaa.rest.result.bizassert.BizCode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 代码扫描接口
 * @author Vettel
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v2")
@Slf4j
public class RepoScanController {
    @Autowired
    private RepoScanService repoScanService;
    /**
     *
     */
    @ApiOperation("")
    @PostMapping(value = "/repo/scan")
    public String repoScan(@RequestHeader(HttpHeaders.X_LOGIN_USER) Long loginUserId , @RequestBody RepoScanDTO repoScanDTO) throws Exception {
        BizAssert.validParam(loginUserId != null && loginUserId != 0,
                new BizCode(BizCodes.INVALID_PARAM.getCode(), "用户未登录"));
        return this.repoScanService.creatScan(loginUserId,repoScanDTO.getCodePath(),repoScanDTO.getProjectName(),repoScanDTO.getSonarToken(),repoScanDTO.getCodeUser(),repoScanDTO.getCodePwd());
    }

}
