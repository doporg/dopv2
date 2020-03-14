package com.clsaa.dop.server.testing.config;

import com.clsaa.dop.server.testing.util.SonarScannerLogger;
import org.sonarsource.scanner.api.EmbeddedScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * SonarScanner配置类
 * @author Vettel
 *
 */
@Configuration
public class SonarScannerConfig {
    @Bean
    public EmbeddedScanner getSonarScanner(){
        EmbeddedScanner scanner = EmbeddedScanner.create("Maven", "3.6.1", new SonarScannerLogger());
        Map<String,String> sonarPropertiesMap = new HashMap<>();
        sonarPropertiesMap.put("sonar.host.url", "http://192.168.31.151:9000");
        sonarPropertiesMap.put("sonar.sourceEncoding", "UTF-8");
        scanner.addGlobalProperties(sonarPropertiesMap);
        return scanner;
    }
}
