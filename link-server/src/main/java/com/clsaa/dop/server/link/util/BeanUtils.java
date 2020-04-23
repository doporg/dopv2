package com.clsaa.dop.server.link.util;

public class BeanUtils {
    private BeanUtils() {
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
        org.springframework.beans.BeanUtils.copyProperties(source, result);
        return result;
    }

}
