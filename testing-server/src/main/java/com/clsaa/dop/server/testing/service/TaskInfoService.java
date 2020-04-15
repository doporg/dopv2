package com.clsaa.dop.server.testing.service;


import com.clsaa.dop.server.testing.dao.UserProjectMappingRepository;
import com.clsaa.dop.server.testing.manage.SonarRestService;
import com.clsaa.dop.server.testing.model.bo.ScanIssuesBO;
import com.clsaa.dop.server.testing.model.bo.SourcesBO;
import com.clsaa.dop.server.testing.model.bo.TaskInfoBO;
import com.clsaa.dop.server.testing.model.bo.TaskMeasuresBO;
import com.clsaa.dop.server.testing.model.po.UserProjectMapping;
import com.clsaa.dop.server.testing.model.vo.TaskInfoVO;
import com.clsaa.dop.server.testing.util.MyBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    @Autowired
    private SonarRestService sonarRestService;

    public List<TaskInfoVO> getAllTasks(Long userId){
        List<UserProjectMapping> userProjectMappingsByUserId = userProjectMappingRepository.findUserProjectMappingsByUserId(userId);
        List<TaskInfoVO> result = new ArrayList<>();
        userProjectMappingsByUserId.forEach(e->{
            TaskInfoBO forObject = sonarRestService.getTaskInfo(e.getProjectKey());
            log.info(String.valueOf(forObject));
            TaskInfoVO taskInfoVO = MyBeanUtils.convertType(forObject.getCurrent(),TaskInfoVO.class);
            taskInfoVO.setStartType(e.getStartType());
            result.add(taskInfoVO);
        });
         Collections.reverse(result);
         return result;
    }

    public void getScanResult(String projectKey){

    }

    public ScanIssuesBO getAllIssues(String projectKey){
            return sonarRestService.getAllIssues(projectKey);
    }


    public TaskMeasuresBO getGeneralInfo(String projectKey){
        return sonarRestService.getGeneralScanInfo(projectKey);
    }

    public SourcesBO getSources(String key){
        return sonarRestService.getSources(key);
    }
}
