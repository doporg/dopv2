package com.clsaa.dop.server.testing.util;

import org.sonarsource.scanner.api.ScanProperties;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ScannerPropertiesUtil {
    public static Map<String,String> scannerProperties(LanguageType type){
        switch (type){
            case JAVA: return JAVA_SCANNER_PROPERTIES;
            case GO: return GO_SCANNER_PROPERTIES;
            case NODE:return NODE_SCANNER_PROPERTIES;
            case PYTHON:return PYTHON_SCANNER_PROPERTIES;
            default: throw new UnsupportedOperationException();
        }
    }

    private static Map<String,String> JAVA_SCANNER_PROPERTIES = new HashMap<String, String>(){{
         put(ScanProperties.PROJECT_SOURCE_DIRS, "src"+ File.separator+"main");
         put(ScanProperties.PROJECT_SOURCE_ENCODING, "UTF-8");
         put("sonar.java.binaries", "target"+File.separator+"classes");
    }};

    private static Map<String,String> PYTHON_SCANNER_PROPERTIES = new HashMap<String, String>(){{}};

    private static Map<String,String> GO_SCANNER_PROPERTIES = new HashMap<String, String>(){{}};

    private static Map<String,String> NODE_SCANNER_PROPERTIES = new HashMap<String, String>(){{}};
}
