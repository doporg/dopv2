package com.clsaa.dop.server.testing.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class ProjectKeyGeneratorTest {

    @Test
    public void generateProjectKey() {
        log.info("ProjectKey:{}",ProjectKeyGenerator.generateProjectKey("test"));
    }
}