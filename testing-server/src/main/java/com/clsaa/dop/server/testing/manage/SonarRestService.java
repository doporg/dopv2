package com.clsaa.dop.server.testing.manage;

import com.clsaa.dop.server.testing.config.SonarConfig;
import com.clsaa.dop.server.testing.model.bo.TaskInfoBO;
import com.clsaa.dop.server.testing.model.bo.TaskMeasureBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 与SonarWebServer交互
 * @author Vettel
 */
@Service
public class SonarRestService {
    @Autowired
    private RestTemplate restTemplate;

    public TaskInfoBO getTaskInfo(String projectKey){
        String uri = SonarConfig.SONAR_SERVER_URL+ "/api/ce/component?component={projectKey}";
        URI build = UriComponentsBuilder.fromUriString(uri).build(projectKey);
        TaskInfoBO forObject = restTemplate.getForObject(build, TaskInfoBO.class);
        return  forObject;
    }

    public TaskMeasureBO getQualityGate(String projectKey){
        String uri = SonarConfig.SONAR_SERVER_URL + "/api/measures/search?projectKeys={projectKey}&metricKeys=alert_status";
        URI build = UriComponentsBuilder.fromUriString(uri).build(projectKey);
        TaskMeasureBO[] forObject = restTemplate.getForObject(build, TaskMeasureBO[].class);
        return forObject[0];
        /*HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity entity = new HttpEntity(headers);
        ParameterizedTypeReference<List<TaskMeasureBO>> responseType = new ParameterizedTypeReference<List<TaskMeasureBO>>() {};
        Map<String,String> params = new HashMap<>();
        params.put("projectKey",projectKey);
        ResponseEntity<List<TaskMeasureBO>> resp = restTemplate.exchange(uri, HttpMethod.GET,entity, responseType,params);
        List<TaskMeasureBO> list = resp.getBody();
        return list.get(0);*/
    }
}
