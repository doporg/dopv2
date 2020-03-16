package com.clsaa.dop.server.testing.service;


import com.clsaa.dop.server.testing.config.SonarConfig;
import com.clsaa.dop.server.testing.dao.UserProjectMappingRepository;
import com.clsaa.dop.server.testing.model.po.UserProjectMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.spring.web.json.Json;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务信息服务类
 * @author Vettel
 */
@Service
@Slf4j
public class TaskInfoService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserProjectMappingRepository userProjectMappingRepository;

    public void getAllTasks(Long userId){
        List<String> projectList = userProjectMappingRepository.findUserProjectMappingsByUserId(userId).stream().map(UserProjectMapping::getProjectKey).collect(Collectors.toList());
        String uri = SonarConfig.SONAR_SERVER_URL+ "/api/ce/component?compoent={projectKey}";
        projectList.forEach(e->{
            URI build = UriComponentsBuilder.fromUriString(uri).build(e);
            Json forObject = restTemplate.getForObject(build, Json.class);
            log.info(String.valueOf(forObject));
        });

    }
}
