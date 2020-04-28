package com.clsaa.dop.server.link.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;
import java.nio.charset.Charset;

public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

    private DefaultResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return errorHandler.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = HttpStatus.resolve(response.getRawStatusCode());
        if (statusCode == null) {
            throw new UnknownHttpStatusCodeException(response.getRawStatusCode(), response.getStatusText(), response.getHeaders(), this.getResponseBody(response), this.getCharset(response));
        } else {
            this.handleError(response, statusCode);
        }
    }

    protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
        String statusText = response.getStatusText();
        HttpHeaders headers = response.getHeaders();
        byte[] body = this.getResponseBody(response);
        Charset charset = this.getCharset(response);
        switch(statusCode.series()) {
            case CLIENT_ERROR:
                if (statusCode == HttpStatus.NOT_FOUND) {
                    throw new NotFoundTraceException(statusCode.value(), "Not Found");
                }
                throw HttpClientErrorException.create(statusCode, statusText, headers, body, charset);
            case SERVER_ERROR:
                throw HttpServerErrorException.create(statusCode, statusText, headers, body, charset);
            default:
                throw new UnknownHttpStatusCodeException(statusCode.value(), statusText, headers, body, charset);
        }
    }

}
