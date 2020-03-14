package com.clsaa.dop.server.testing.controller;


import com.clsaa.dop.server.testing.model.dto.RepoScanDTO;
import com.clsaa.dop.server.testing.service.RepoScanService;
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
    public void repoScan(@RequestBody RepoScanDTO repoScanDTO) throws Exception {
        this.repoScanService.creatScan(repoScanDTO.getCodePath(),repoScanDTO.getProjectName(),repoScanDTO.getSonarToken(),repoScanDTO.getCodeUser(),repoScanDTO.getCodePwd());
    }

}
