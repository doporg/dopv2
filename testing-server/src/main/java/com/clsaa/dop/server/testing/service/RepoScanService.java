package com.clsaa.dop.server.testing.service;


import com.clsaa.dop.server.testing.config.BizCodes;
import com.clsaa.dop.server.testing.config.SonarConfig;
import com.clsaa.dop.server.testing.dao.UserProjectMappingRepository;
import com.clsaa.dop.server.testing.manage.SonarRestService;
import com.clsaa.dop.server.testing.model.bo.CIScanBO;
import com.clsaa.dop.server.testing.model.enums.StartType;
import com.clsaa.dop.server.testing.model.po.UserProjectMapping;
import com.clsaa.dop.server.testing.util.LanguageType;
import com.clsaa.dop.server.testing.util.ProjectKeyGenerator;
import com.clsaa.dop.server.testing.util.ScannerPropertiesUtil;
import com.clsaa.dop.server.testing.util.SonarScannerLogger;
import com.clsaa.rest.result.bizassert.BizAssert;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.sonarsource.scanner.api.EmbeddedScanner;
import org.sonarsource.scanner.api.ScanProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
    private SonarRestService sonarRestService;

    public String quickScan(Long userId, String codePath, String projectName, String codeUser, String codePwd, LanguageType languageType,StartType startType){
        String projectKey = ProjectKeyGenerator.generateProjectKey(projectName);

        //mkdir
        File localPath = null;
        try {
           localPath = creteTempDir();
           localPath.delete();
        } catch (IOException e) {
            BizAssert.justFailed(BizCodes.FILE_OPERATE);
        }

        //clone
        try(Git result = gitClone(codePath,localPath,codeUser,codePwd)){
            log.info("Having repository: " + result.getRepository().getDirectory());
        } catch (GitAPIException e) {
            BizAssert.justFailed(BizCodes.GIT_OPERATE);
        }

        //setTaskProperties
        Map<String,String> projectSettingMap = ScannerPropertiesUtil.scannerProperties(languageType);
        projectSettingMap.put(ScanProperties.PROJECT_KEY, projectKey);
        projectSettingMap.put(ScanProperties.PROJECT_BASEDIR, localPath.getAbsolutePath());

        try {
            executeScan(projectSettingMap);
            UserProjectMapping userProjectMapping = UserProjectMapping.builder().userId(userId).projectKey(projectKey).startType(startType).build();
            this.userProjectMappingRepository.saveAndFlush(userProjectMapping);
        }finally {
            try {
                FileUtils.deleteDirectory(localPath);
            } catch (IOException e) {
                BizAssert.justFailed(BizCodes.FILE_OPERATE);
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

    private Git gitClone(String codePath,String branch,File localPath,String codeUser,String codePwd) throws GitAPIException {
        return Git.cloneRepository()
                .setURI(codePath)
                .setBranch(branch)
                .setDirectory(localPath)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(codeUser,codePwd))
                .call();
    }
    private String getRevision(Git git) throws GitAPIException {
        Iterable<RevCommit> call = git.log().setMaxCount(1).call();
        List<RevCommit> revCommits = Lists.newArrayList(call);
        return  revCommits.get(0).getName().substring(0,7);
    }
    public CIScanBO ciScan(Long userId,String codePath,String branch,String codeUser,String codePwd,LanguageType languageType,StartType startType){
        String projectKey = ProjectKeyGenerator.testGenerate(codePath,branch);
        log.info("ProjectKey:{}",projectKey);
        //mkdir
        File localPath = null;
        try {
            localPath = creteTempDir();
            boolean delete = localPath.delete();
            log.info("File Delete:{}",delete);
        } catch (IOException e) {
            BizAssert.justFailed(BizCodes.FILE_OPERATE);
        }

        //clone
        String revision = null;
        try {
            Git result = gitClone(codePath,branch,localPath,codeUser,codePwd);
            revision = getRevision(result);
            log.info("Having repository: " + result.getRepository().getDirectory());
            log.info("Revision:{}",revision);
            result.close();
        }catch (GitAPIException e){
            log.error("Git Clone fail",e);
            BizAssert.justFailed(BizCodes.GIT_OPERATE);
        }

        //setTaskProperties
        Map<String,String> projectSettingMap = ScannerPropertiesUtil.scannerProperties(languageType);
        projectSettingMap.put(ScanProperties.PROJECT_KEY, projectKey);
        projectSettingMap.put(ScanProperties.PROJECT_BASEDIR, localPath.getAbsolutePath());
        projectSettingMap.put(ScanProperties.PROJECT_VERSION,revision);

        try {
            log.info("Begin Scan");
            executeScan(projectSettingMap);
            log.info("End Scan");
            UserProjectMapping userProjectMapping = UserProjectMapping.builder().userId(userId).projectKey(projectKey).startType(startType).build();
            this.userProjectMappingRepository.saveAndFlush(userProjectMapping);
        }finally {
            try {
                log.info("Begin File Delete");
                FileUtils.deleteDirectory(localPath);
            } catch (IOException e) {
                log.info("File Operate");
                BizAssert.justFailed(BizCodes.FILE_OPERATE);
            }
        }
        return CIScanBO.builder().projectKey(projectKey).codePath(codePath).version(revision).branch(branch).build();
    }

}
