package com.clsaa.dop.server.testing.config;

import com.clsaa.dop.server.testing.util.SonarScannerLogger;
import org.sonarsource.scanner.api.EmbeddedScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Sonar配置类
 * @author Vettel
 *
 */

@Configuration
public class SonarConfig {
    public static final String SONAR_SERVER_URL = "http://192.168.31.151:9000";
    public static final String SONAR_TOKEN = "8aee6a6036c773efe6209370c30e3b7b86e02285";

    @Bean
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
    }
}
