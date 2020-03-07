package com.clsaa.dop.server.testing.controller;


import com.sun.javaws.Main;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.sonarsource.scanner.api.EmbeddedScanner;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

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
    /**
     *
     */
    @ApiOperation("")
    @PostMapping("/quickscan")
    public void quickScan(@RequestParam(value = "code_path") String codePath,@RequestParam("project_name") String projectName)throws IOException, GitAPIException {
        File localPath = File.createTempFile("TestGitRepository", "");
        if(!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }

        // then clone
        log.info("Cloning from " + codePath + " to " + localPath);
        try (Git result = Git.cloneRepository()
                .setURI(codePath)
                .setDirectory(localPath)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider("vettelx","xqzxcqweasd12345"))
                .call()) {
            // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
            log.info("Having repository: " + result.getRepository().getDirectory());
        }

        // clean up here to not keep using more and more disk-space for these samples
        //FileUtils.deleteDirectory(localPath);



    }

}
