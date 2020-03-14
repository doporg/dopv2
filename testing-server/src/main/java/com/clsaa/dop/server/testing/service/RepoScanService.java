package com.clsaa.dop.server.testing.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.sonarsource.scanner.api.EmbeddedScanner;
import org.sonarsource.scanner.api.ScanProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *代码扫描服务类
 * @author Vettel
 */

@Service
@Slf4j
public class RepoScanService {
    @Autowired
    private EmbeddedScanner embeddedScanner;

    public void creatScan(String codePath,String projectName,String sonarToken,String codeUser,String codePwd) throws Exception {
        //set sonar token
        log.info(embeddedScanner.globalProperties().get(""));
        Map<String,String> sonarProperties = new HashMap<>();
        sonarProperties.put("sonar.login",sonarToken);
        embeddedScanner.addGlobalProperties(sonarProperties);

        File localPath = File.createTempFile("TestGitRepository", "");
        if(!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }

        //clone
        log.info("Cloning from " + codePath + " to " + localPath);
        try (Git result = Git.cloneRepository()
                .setURI(codePath)
                .setDirectory(localPath)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(codeUser,codePwd))
                .call()) {
            // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
            log.info("Having repository: " + result.getRepository().getDirectory());
        }
        Map<String, String> projectSettingMap = new LinkedHashMap<>();
        projectSettingMap.put(ScanProperties.PROJECT_KEY, projectName);
        projectSettingMap.put(ScanProperties.PROJECT_BASEDIR, localPath.getAbsolutePath());
        projectSettingMap.put(ScanProperties.PROJECT_SOURCE_DIRS, "src\\main\\java");
        projectSettingMap.put(ScanProperties.PROJECT_SOURCE_ENCODING, "UTF-8");
        projectSettingMap. put("sonar.java.binaries", "target\\classes");
//        projectSettingMap.put("sonar.java.source", "src/main/java");

        embeddedScanner.start();
        embeddedScanner.execute(projectSettingMap);


        FileUtils.deleteDirectory(localPath);
    }


}
