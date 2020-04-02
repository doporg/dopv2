package com.clsaa.dop.server.testing.util;

import java.util.UUID;

/**
 * projectKey生成器
 * @author Vettel
 * @
 */
public class ProjectKeyGenerator {
    public static String generateProjectKey(String projectName){
        return  projectName +"_"+ UUID.randomUUID().toString();
    }
}
