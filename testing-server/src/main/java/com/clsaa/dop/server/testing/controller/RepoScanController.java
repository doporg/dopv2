package com.clsaa.dop.server.testing.controller;


import com.clsaa.dop.server.testing.config.BizCodes;
import com.clsaa.dop.server.testing.config.HttpHeaders;
import com.clsaa.dop.server.testing.model.dto.CIScanDTO;
import com.clsaa.dop.server.testing.model.dto.RepoScanDTO;
import com.clsaa.dop.server.testing.model.enums.StartType;
import com.clsaa.dop.server.testing.model.vo.CIScanVO;
import com.clsaa.dop.server.testing.service.RepoScanService;
import com.clsaa.dop.server.testing.util.MyBeanUtils;
import com.clsaa.rest.result.bizassert.BizAssert;
import com.clsaa.rest.result.bizassert.BizCode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 代码扫描接口
 *
 * @author Vettel
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v2/scan")
@Slf4j
public class RepoScanController {
    @Autowired
    private RepoScanService repoScanService;

    @ApiOperation(value = "CI代码扫描")
    @PostMapping(value = "/ci")
    public CIScanVO Scan(@ApiParam(value = "用户id")@RequestHeader(HttpHeaders.X_LOGIN_USER) Long loginUserId, @RequestBody CIScanDTO ciScanDTO) {
        BizAssert.validParam(loginUserId != null && loginUserId != 0,
                new BizCode(BizCodes.INVALID_PARAM.getCode(), "用户未登录"));
        return MyBeanUtils.convertType(this.repoScanService.ciScan(loginUserId,
                ciScanDTO.getCodePath(),
                ciScanDTO.getBranch(),
                ciScanDTO.getCodeUser(),
                ciScanDTO.getCodePwd(),
                ciScanDTO.getType(),
                StartType.CI), CIScanVO.class);
    }

    @ApiOperation(value = "快速扫描")
    @PostMapping(value = "/quick")
    public String quickScan(@ApiParam(value = "用户id")@RequestHeader(HttpHeaders.X_LOGIN_USER) Long loginUserId, @RequestBody RepoScanDTO repoScanDTO) {
        BizAssert.validParam(loginUserId != null && loginUserId != 0,
                new BizCode(BizCodes.INVALID_PARAM.getCode(), "用户未登录"));
        return this.repoScanService.quickScan(loginUserId,
                repoScanDTO.getCodePath(),
                repoScanDTO.getProjectName(),
                repoScanDTO.getCodeUser(),
                repoScanDTO.getCodePwd(),
                repoScanDTO.getType(),
                StartType.PLATFORM);
    }




}
