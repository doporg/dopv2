package com.clsaa.dop.server.testing.controller;


import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.sonarsource.scanner.api.EmbeddedScanner;
import org.sonarsource.scanner.api.ScanProperties;
import org.sonarsource.scanner.api.StdOutLogOutput;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 代码扫描接口
 * @author Vettel
 *
 *
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
        Map<String, String> sonarPropertiesMap = new LinkedHashMap<String, String>();
        sonarPropertiesMap.put("sonar.host.url", "http://192.168.31.151:9000");
        sonarPropertiesMap.put("sonar.sourceEncoding", "UTF-8");
        sonarPropertiesMap.put("sonar.login", "4e8fccc6f61e49e681e91526ecac77b16afebb95");

        Map<String, String> projectSettingMap = new LinkedHashMap<>();
        projectSettingMap.put(ScanProperties.PROJECT_KEY, "test_project_01");
        projectSettingMap.put(ScanProperties.PROJECT_BASEDIR, localPath.getAbsolutePath());
        projectSettingMap.put(ScanProperties.PROJECT_SOURCE_DIRS, "src/main/java");
        projectSettingMap.put(ScanProperties.PROJECT_SOURCE_ENCODING, "UTF-8");
        projectSettingMap. put("sonar.java.binaries", "target/classes");
        projectSettingMap.put("sonar.java.source", "src/main/java");

        EmbeddedScanner scanner = EmbeddedScanner.create("Maven", "3.6.1", new StdOutLogOutput());
        scanner.addGlobalProperties(sonarPropertiesMap);
        scanner.start();
        scanner.execute(projectSettingMap);


    }

}
