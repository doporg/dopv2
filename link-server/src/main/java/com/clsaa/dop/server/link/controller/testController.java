package com.clsaa.dop.server.link.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.*;

@RestController
@CrossOrigin
public class testController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/getProjectList")
    public List<Project> getProjectList(String userId) {
        List<Project> result = new ArrayList<>();
        result.add(new Project(1L,"project1"));
        result.add(new Project(2L,"project2"));
        result.add(new Project(3L,"project3"));
        result.add(new Project(4L,"project4"));
        result.add(new Project(5L,"project5"));
        result.add(new Project(6L,"project6"));
        result.add(new Project(7L,"project7"));
        result.add(new Project(8L,"project8"));
        result.add(new Project(9L,"project9"));
        result.add(new Project(10L,"project10"));
        result.add(new Project(11L,"project11"));
        result.add(new Project(12L,"project12"));
        result.add(new Project(13L,"project13"));
        result.add(new Project(14L,"project14"));
        result.add(new Project(15L,"project15"));
        result.add(new Project(16L,"project16"));
        result.add(new Project(17L,"project17"));
        result.add(new Project(18L,"project18"));
        result.add(new Project(19L,"project19"));
        result.add(new Project(20L,"project20"));
        return result;
    }

    @Data
    class Project {

        private Long id;
        private String name;

        Project(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @GetMapping(value = "/getServiceList")
    public String[] getServiceListByProjectID(String projectID) {
        return new String[]{
                "project" + projectID + "/service1",
                "project" + projectID + "/service2",
                "project" + projectID + "/service3"
        };
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        System.out.println("xxxxx");
        String s =  restTemplate.getForObject(
                "http://link-server/api/v2/services", String.class);
        System.err.println(s);
        return s;
    }
}
