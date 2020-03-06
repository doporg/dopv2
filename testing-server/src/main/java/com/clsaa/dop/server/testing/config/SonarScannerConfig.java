package com.clsaa.dop.server.testing.config;

import org.sonarsource.scanner.api.EmbeddedScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SonarScanner配置类
 * @author Vettel
 *
 */
@Configuration
public class SonarScannerConfig {
    @Bean
    public EmbeddedScanner getSonarScanner(){
        return null;
    }
}
