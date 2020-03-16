package com.clsaa.dop.server.testing.service;


import com.clsaa.dop.server.testing.config.SonarConfig;
import com.clsaa.dop.server.testing.dao.UserProjectMappingRepository;
import com.clsaa.dop.server.testing.model.po.UserProjectMapping;
import com.clsaa.dop.server.testing.util.ProjectKeyGenerator;
import com.clsaa.dop.server.testing.util.SonarScannerLogger;
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
    private UserProjectMappingRepository userProjectMappingRepository;

    public String creatScan(Long userId,String codePath,String projectName,String sonarToken,String codeUser,String codePwd) throws Exception {
        String projectKey = ProjectKeyGenerator.generateProjectKey(projectName);
        //sonarscanner
        EmbeddedScanner scanner = EmbeddedScanner.create("Maven", "3.6.1", new SonarScannerLogger());
        Map<String,String> sonarPropertiesMap = new HashMap<>();
        sonarPropertiesMap.put("sonar.host.url", SonarConfig.SONAR_SERVER_URL);
        sonarPropertiesMap.put("sonar.sourceEncoding", "UTF-8");
        sonarPropertiesMap.put("sonar.login",sonarToken);
        scanner.addGlobalProperties(sonarPropertiesMap);

        //mkdir
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
        projectSettingMap.put(ScanProperties.PROJECT_KEY, projectKey);
        projectSettingMap.put(ScanProperties.PROJECT_BASEDIR, localPath.getAbsolutePath());
        projectSettingMap.put(ScanProperties.PROJECT_SOURCE_DIRS, "src\\main\\java");
        projectSettingMap.put(ScanProperties.PROJECT_SOURCE_ENCODING, "UTF-8");
        projectSettingMap. put("sonar.java.binaries", "target\\classes");

        scanner.start();
        try {
            scanner.execute(projectSettingMap);
        }catch (Exception e){
            log.error("Exception",e);
        }finally {
            UserProjectMapping userProjectMapping = UserProjectMapping.builder().userId(userId).projectKey(projectKey).build();
            this.userProjectMappingRepository.saveAndFlush(userProjectMapping);
            FileUtils.deleteDirectory(localPath);
        }

        return projectKey;

    }


}
