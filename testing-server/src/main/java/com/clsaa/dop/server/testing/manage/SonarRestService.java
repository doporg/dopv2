package com.clsaa.dop.server.testing.manage;

import com.clsaa.dop.server.testing.config.SonarConfig;
import com.clsaa.dop.server.testing.model.bo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 与Sonar WebServer交互
 * @author Vettel
 */
@Service
@Slf4j
public class SonarRestService {
    @Autowired
    private RestTemplate restTemplate;

    public TaskInfoBO getTaskInfo(String projectKey){
        String uri = SonarConfig.SONAR_SERVER_URL+ "/api/ce/component?componentKey={projectKey}";
        URI build = UriComponentsBuilder.fromUriString(uri).build(projectKey);
        TaskInfoBO forObject = restTemplate.getForObject(build, TaskInfoBO.class);
        return  forObject;
    }

    public TaskMeasureBO getQualityGate(String projectKey){
        String uri = SonarConfig.SONAR_SERVER_URL + "/api/measures/search?projectKeys={projectKey}&metricKeys=alert_status";
        URI build = UriComponentsBuilder.fromUriString(uri).build(projectKey);
        TaskMeasuresBO forObject = restTemplate.getForObject(build, TaskMeasuresBO.class);
        log.info("Measures:{}",forObject);
        return forObject.getMeasures().get(0);
    }
    public ScanIssuesBO getAllIssues(String projectKey){
        String uri = SonarConfig.SONAR_SERVER_URL+"/api/issues/search?componentKeys={projectKey}&types=BUG,CODE_SMELL,VULNERABILITY";
        URI build = UriComponentsBuilder.fromUriString(uri).build(projectKey);
        return restTemplate.getForObject(build, ScanIssuesBO.class);
    }

    public TaskMeasuresBO getGeneralScanInfo(String projectKey){
        String uri = SonarConfig.SONAR_SERVER_URL+"/api/measures/search?projectKeys={projectKey}&metricKeys=" +
                "alert_status," +
                "bugs," +
                "new_bugs,"+
                "vulnerabilities,"+
                "new_vulnerabilities,"+
                "reliability_rating," +
                "code_smells,"+
                "new_code_smells,"+
                "security_rating," +
                "sqale_rating," +
                "sqale_index," +
                "coverage," +
                "duplicated_lines_density," +
                "duplicated_blocks," +
                "ncloc," +
                "ncloc_language_distribution";
        URI build = UriComponentsBuilder.fromUriString(uri).build(projectKey);
        return restTemplate.getForObject(build, TaskMeasuresBO.class);

    }

    public SourcesBO getSources(String key){
        String uri = SonarConfig.SONAR_SERVER_URL+"/api/sources/lines?key={key}";
        URI build = UriComponentsBuilder.fromUriString(uri).build(key);
        return restTemplate.getForObject(build,SourcesBO.class);
    }
}
