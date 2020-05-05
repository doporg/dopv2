package com.clsaa.dop.server.testing.config;



import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

/**
 * RestTemplate配置
 */
@Configuration
public class RestTemplateConfig {
    public class AgentInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            org.springframework.http.HttpHeaders headers = request.getHeaders();
            String decode = SonarConfig.SONAR_TOKEN+ ":" ;
            byte[] decodeByte = decode.getBytes(StandardCharsets.UTF_8);
            String encoded = Base64.getEncoder().encodeToString(decodeByte);
            headers.add(org.springframework.http.HttpHeaders.AUTHORIZATION, "Basic " + encoded);
            headers.add(org.springframework.http.HttpHeaders.CONTENT_TYPE, "application/json");
            headers.add(HttpHeaders.USER_AGENT, "");
            return execution.execute(request, body);
        }
    }

    @Bean
    public RestTemplate restTemplate(){
        HttpComponentsClientHttpRequestFactory httpRequestFactory  = new HttpComponentsClientHttpRequestFactory ();
        httpRequestFactory .setConnectTimeout(1000);
        httpRequestFactory .setReadTimeout(1000);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.setInterceptors(Collections.singletonList(new AgentInterceptor()));
        return restTemplate;
    }
}
