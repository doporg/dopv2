package com.clsaa.dop.server.testing.util;

import java.util.UUID;

public class ProjectKeyGenerator {
    public static String generateProjectKey(String projectName){
        return  "scan"+projectName +"_"+ UUID.randomUUID().toString();
    }
}
