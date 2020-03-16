package com.clsaa.dop.server.testing.service;

import com.clsaa.dop.server.testing.config.SonarConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RestTemplateTest {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void getAllTasks() {
        String uri = SonarConfig.SONAR_SERVER_URL+ "/api/ce/component?component={projectKey}";
        String projectKey = "project01a1ad2b47-ce2e-40f4-beda-d5d519de7fe3";
        URI build = UriComponentsBuilder.fromUriString(uri).build(projectKey);
        String forObject = restTemplate.getForObject(build, String.class);
        log.info(forObject);
    }
}