package com.clsaa.dop.server.testing.util;

import org.springframework.beans.BeanUtils;

public class MyBeanUtils {
    private MyBeanUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 从类型转换
     */
    public static <T, R> R convertType(T source, Class<R> targetClass) {
        if (source == null) {
            return null;
        }
        final R result;
        try {
            result = targetClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BeanUtils.copyProperties(source, result);
        return result;
    }

}
