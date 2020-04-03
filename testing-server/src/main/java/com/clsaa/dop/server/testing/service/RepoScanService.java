package com.clsaa.dop.server.testing.service;


import com.clsaa.dop.server.testing.config.SonarConfig;
import com.clsaa.dop.server.testing.dao.UserProjectMappingRepository;
import com.clsaa.dop.server.testing.manage.SonarRestService;
import com.clsaa.dop.server.testing.model.po.UserProjectMapping;
import com.clsaa.dop.server.testing.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
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
    @Autowired
    private EmbeddedScanner scanner;
    @Autowired
    private SonarRestService sonarRestService;
    public String quickScan(Long userId, String codePath, String projectName, String codeUser, String codePwd) {
        String projectKey = ProjectKeyGenerator.generateProjectKey(projectName);
        //mkdir
        File localPath = null;
        try {
             localPath = File.createTempFile("TestGitRepository", "");
        }catch (IOException e){
            e.printStackTrace();
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
        } catch (InvalidRemoteException e) {
            e.printStackTrace();
        } catch (TransportException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
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
            UserProjectMapping userProjectMapping = UserProjectMapping.builder().userId(userId).projectKey(projectKey).build();
            this.userProjectMappingRepository.saveAndFlush(userProjectMapping);
        }catch (Exception e){
            log.error("Exception",e);
        }finally {
            try {
                FileUtils.deleteDirectory(localPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return projectKey;

    }

    public String repoScan(Long userId, String codePath, String projectName, String codeUser, String codePwd, LanguageType languageType){
        String projectKey = ProjectKeyGenerator.generateProjectKey(projectName);

        //mkdir
        File localPath = null;
        try {
           localPath = creteTempDir();
           localPath.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //clone
        try(Git result = gitClone(codePath,localPath,codeUser,codePwd)){
            log.info("Having repository: " + result.getRepository().getDirectory());
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        //setTaskProperties
        Map<String,String> projectSettingMap = ScannerPropertiesUtil.scannerProperties(languageType);
        projectSettingMap.put(ScanProperties.PROJECT_KEY, projectKey);
        projectSettingMap.put(ScanProperties.PROJECT_BASEDIR, localPath.getAbsolutePath());

        try {
            executeScan(projectSettingMap);
            UserProjectMapping userProjectMapping = UserProjectMapping.builder().userId(userId).projectKey(projectKey).startType(StartType.CI).build();
            this.userProjectMappingRepository.saveAndFlush(userProjectMapping);
        }finally {
            try {
                FileUtils.deleteDirectory(localPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return projectKey;
    }


    private File creteTempDir() throws IOException {
        return File.createTempFile("DopGitRepository","");
    }


    private Git  gitClone(String codePath,File localPath,String codeUser,String codePwd) throws GitAPIException {
        return Git.cloneRepository()
                .setURI(codePath)
                .setDirectory(localPath)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(codeUser,codePwd))
                .call();
    }

    private void executeScan(Map<String,String > projectSettingMap){
        EmbeddedScanner scanner = EmbeddedScanner.create("Maven", "3.6.1", new SonarScannerLogger());
        Map<String,String> sonarPropertiesMap = new HashMap<>();
        sonarPropertiesMap.put("sonar.host.url", SonarConfig.SONAR_SERVER_URL);
        sonarPropertiesMap.put("sonar.sourceEncoding", "UTF-8");
        sonarPropertiesMap.put("sonar.login",SonarConfig.SONAR_TOKEN);
        scanner.addGlobalProperties(sonarPropertiesMap);
        scanner.start();
        scanner.execute(projectSettingMap);
    }

}
