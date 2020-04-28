package com.clsaa.dop.server.link.util;

import com.clsaa.rest.result.exception.AbstractResultException;
import org.springframework.http.HttpStatus;

public class NotFoundTraceException extends AbstractResultException {

    private static final long serialVersionUID = 1L;

    public NotFoundTraceException(int code, String message) {
        super(code, message, HttpStatus.NOT_FOUND);
    }
}
