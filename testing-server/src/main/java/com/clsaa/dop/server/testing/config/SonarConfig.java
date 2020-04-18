package com.clsaa.dop.server.testing.config;

import org.springframework.context.annotation.Configuration;

/**
 * Sonar配置类
 * @author Vettel
 *
 */

@Configuration
public class SonarConfig {
    public static final String SONAR_SERVER_URL = "http://115.28.186.77:9000";
    public static final String SONAR_TOKEN = "0f3f04560a0fb62bf8173fa509f578d1f3cebdd1";

    /*@Bean
    public EmbeddedScanner embeddedScanner(){
        //sonarscanner
        EmbeddedScanner scanner = EmbeddedScanner.create("Maven", "3.6.1", new SonarScannerLogger());
        Map<String,String> sonarPropertiesMap = new HashMap<>();
        sonarPropertiesMap.put("sonar.host.url", SONAR_SERVER_URL);
        sonarPropertiesMap.put("sonar.sourceEncoding", "UTF-8");
        sonarPropertiesMap.put("sonar.login",SONAR_TOKEN);
        scanner.addGlobalProperties(sonarPropertiesMap);
        scanner.start();
        return scanner;
    }*/
}
