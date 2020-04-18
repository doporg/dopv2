package com.clsaa.dop.server.testing.util;

import org.sonarsource.scanner.api.ScanProperties;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ScannerPropertiesUtil {
    public static Map<String,String> scannerProperties(LanguageType type){
        /*switch (type){
            case JAVA: return JAVA_SCANNER_PROPERTIES;
            case GO: return GO_SCANNER_PROPERTIES;
            case JAVASCRIPT:return JAVASCRIPT_SCANNER_PROPERTIES;
            case PYTHON:return PYTHON_SCANNER_PROPERTIES;
            case CSHARP:return CSHARP_SCANNER_PROPERTIES;
            case PHP:return PHP_SCANNER_PROPERTIES;
            default: throw new UnsupportedOperationException();
        }*/
        Map<String,String> map = new HashMap<>();
        map.put(ScanProperties.PROJECT_SOURCE_DIRS,"."+ File.separator);
        map.put(ScanProperties.PROJECT_SOURCE_ENCODING, "UTF-8");

        return map;
    }

    private static Map<String,String> JAVA_SCANNER_PROPERTIES = new HashMap<String, String>(){{
        //"src"+ File.separator+"main"+File.separator+"java"
        put(ScanProperties.PROJECT_SOURCE_DIRS,"."+ File.separator);
         put(ScanProperties.PROJECT_SOURCE_ENCODING, "UTF-8");
    }};

    private static Map<String,String> PYTHON_SCANNER_PROPERTIES = new HashMap<String, String>(){{}};

    private static Map<String,String> GO_SCANNER_PROPERTIES = new HashMap<String, String>(){{}};

    private static Map<String,String> JAVASCRIPT_SCANNER_PROPERTIES = new HashMap<String, String>(){{}};

    private static Map<String,String> CSHARP_SCANNER_PROPERTIES = new HashMap<String, String>(){{}};
    private static Map<String,String> PHP_SCANNER_PROPERTIES = new HashMap<String, String>(){{}};
}
