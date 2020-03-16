package com.clsaa.dop.server.testing.service;


import com.clsaa.dop.server.testing.config.SonarConfig;
import com.clsaa.dop.server.testing.dao.UserProjectMappingRepository;
import com.clsaa.dop.server.testing.model.bo.TaskInfoBO;
import com.clsaa.dop.server.testing.model.po.UserProjectMapping;
import com.clsaa.dop.server.testing.model.vo.TaskInfoVO;
import com.clsaa.dop.server.testing.util.MyBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
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

    public List<TaskInfoVO> getAllTasks(Long userId){
        List<String> projectList = userProjectMappingRepository.findUserProjectMappingsByUserId(userId).stream().map(UserProjectMapping::getProjectKey).collect(Collectors.toList());
        List<TaskInfoVO> result = new ArrayList<>();
        String uri = SonarConfig.SONAR_SERVER_URL+ "/api/ce/component?component={projectKey}";
        projectList.forEach(e->{
            URI build = UriComponentsBuilder.fromUriString(uri).build(e);
            TaskInfoBO forObject = restTemplate.getForObject(build, TaskInfoBO.class);
            log.info(String.valueOf(forObject));
            result.add(MyBeanUtils.convertType(forObject.getCurrent(),TaskInfoVO.class));
        });

        return result;
    }
}
