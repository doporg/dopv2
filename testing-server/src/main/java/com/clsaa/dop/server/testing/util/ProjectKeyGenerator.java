package com.clsaa.dop.server.testing.util;


import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

    public static String testGenerate(String uri ,String branch){
        Base64.Decoder decoder = Base64.getDecoder();
        Base64.Encoder encoder = Base64.getEncoder();
        String text = uri+":"+branch;
        byte[] textByte = text.getBytes(StandardCharsets.UTF_8);
        String encodeText = encoder.encodeToString(textByte);
        return  encodeText;
    }
}
