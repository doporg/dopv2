package com.clsaa.dop.server.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sonarsource.scanner.api.EmbeddedScanner;
import org.sonarsource.scanner.api.LogOutput;
import org.sonarsource.scanner.api.ScanProperties;
import org.sonarsource.scanner.api.StdOutLogOutput;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmbeddedScannerTest {
    private static LogOutput logOutput = new StdOutLogOutput();

    //sonar的配置
    private static Map<String, String> sonarPropertiesMap = new LinkedHashMap<String, String>() {{
        put("sonar.host.url", "http://192.168.31.151:9000");
        put("sonar.sourceEncoding", "UTF-8");
        put("sonar.login", "58b7a32ba35d2093babfc5041fdf0d8c8a9127f7");

    }};

    //项目代码的配置
    private static Map<String, String> projectSettingMap = new LinkedHashMap<String, String>() {{
        put(ScanProperties.PROJECT_KEY, "test_project_01");
        put(ScanProperties.PROJECT_BASEDIR, "C:\\Users\\11056\\Documents\\dopv2\\testing-server");
        put(ScanProperties.PROJECT_SOURCE_DIRS, "src\\main\\java");
        put(ScanProperties.PROJECT_SOURCE_ENCODING, "UTF-8");
        put("sonar.java.binaries", "target\\classes");
        put("sonar.java.source", "src\\main\\java");

    }};

    @Test
    public void test01(){
        EmbeddedScanner scanner = EmbeddedScanner.create("Gradle", "6.7.4", logOutput);
        scanner.addGlobalProperties(sonarPropertiesMap);
        scanner.start();
        scanner.execute(projectSettingMap);
    }


}
