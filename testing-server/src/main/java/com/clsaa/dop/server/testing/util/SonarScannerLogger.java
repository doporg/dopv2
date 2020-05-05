package com.clsaa.dop.server.testing.util;

import lombok.extern.slf4j.Slf4j;
import org.sonarsource.scanner.api.LogOutput;

/**
 * SonarScanner日志输出
 * @author Vettel
 * @version v1
 * @since 2020-03-13
 */
@Slf4j
public class SonarScannerLogger implements LogOutput {
    @Override
    public void log(String formattedMessage, Level level) {
        switch (level){
            case INFO:log.info(formattedMessage);break;
            case WARN:log.warn(formattedMessage);break;
            case DEBUG:log.debug(formattedMessage);break;
            case ERROR:log.error(formattedMessage);break;
            case TRACE:log.trace(formattedMessage);break;
        }
    }
}
