package com.clsaa.dop.server.api.restTemplate;

import com.clsaa.dop.server.api.module.kong.routeModule.KongRoute;
import com.clsaa.dop.server.api.module.kong.serviceModule.KongService;
import com.clsaa.dop.server.api.module.kong.serviceModule.KongServiceList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiRestTemplate {
    private RestTemplate restTemplate;
    private static String serviceUrl;
    private static String routeUrl;

    public ApiRestTemplate() {
        this.restTemplate = new RestTemplate();
    }

    @Value("${api.kong.url}")
    public void setTargetUrl(String targetUrl) {
        ApiRestTemplate.serviceUrl = targetUrl+"/services";
        ApiRestTemplate.routeUrl = targetUrl+"/routes";
    }

    public KongServiceList getRequest(){
         return restTemplate.getForObject(serviceUrl,KongServiceList.class);
    }

    public KongService createService(String host,String name,Long timeout,String protocol,Long port){
        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE,(MediaType.MULTIPART_FORM_DATA_VALUE));

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("host", host);
        map.add("name", name);
        map.add("port", port);
        map.add("protocol",protocol);
        map.add("connect_timeout", timeout);

        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, header);
        try {
            ResponseEntity<KongService> exchangeResult = restTemplate.exchange(serviceUrl, HttpMethod.POST, request, KongService.class);
            //System.out.println(exchangeResult.getStatusCode()+" "+exchangeResult.getStatusCodeValue());
            //System.out.println(JSON.toJSONString(exchangeResult.getBody()));
            if (exchangeResult.getStatusCode().equals(HttpStatus.CREATED)){
                return exchangeResult.getBody();
            }else {
                return null;
            }
        }catch (HttpClientErrorException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public KongService modifyService(String serviceId,String host,String name,Long timeout,String protocol,Long port){
        setUp();

        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE,(MediaType.MULTIPART_FORM_DATA_VALUE));
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("host", host);
        map.add("name", name);
        map.add("port", port);
        map.add("protocol",protocol);
        map.add("connect_timeout", timeout);
        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, header);
        String url = serviceUrl+"/"+serviceId;
        try {
            ResponseEntity<KongService> exchangeResult = restTemplate.exchange(url, HttpMethod.PATCH, request, KongService.class);
            //System.out.println(exchangeResult.getStatusCode()+" "+exchangeResult.getStatusCodeValue());
            //System.out.println(JSON.toJSONString(exchangeResult.getBody()));
            if (exchangeResult.getStatusCode().equals(HttpStatus.OK)){
                return exchangeResult.getBody();
            }else {
                return null;
            }
        }catch (HttpClientErrorException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public boolean deleteService(String serviceId){
        return delete(serviceId, serviceUrl);
    }



    public KongRoute createRoute(String method,String path,String serviceId){
        setUp();

        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE,(MediaType.MULTIPART_FORM_DATA_VALUE));
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("service.id", serviceId);
        map.add("methods[]",method);
        map.add("paths[]", path);

        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, header);
        try {
            ResponseEntity<KongRoute> exchangeResult = restTemplate.exchange(routeUrl, HttpMethod.POST, request, KongRoute.class);
            //System.out.println(exchangeResult.getStatusCode()+" "+exchangeResult.getStatusCodeValue());
            //System.out.println(JSON.toJSONString(exchangeResult.getBody()));
            if (exchangeResult.getStatusCode().equals(HttpStatus.CREATED)){
                return exchangeResult.getBody();
            }else {
                return null;
            }
        }catch (HttpClientErrorException ex){
            ex.printStackTrace();
            return null;
        }
    }
    public KongRoute modifyRoute(String routeId,String method,String path,String serviceId){
        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE,(MediaType.MULTIPART_FORM_DATA_VALUE));

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("service.id", serviceId);
        map.add("methods[]",method);
        map.add("paths[]", path);

        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, header);
        try {
            String url = routeUrl + "/" + routeId;
            ResponseEntity<KongRoute> exchangeResult = restTemplate.exchange(url, HttpMethod.PATCH, request, KongRoute.class);
            //System.out.println(exchangeResult.getStatusCode()+" "+exchangeResult.getStatusCodeValue());
            //System.out.println(JSON.toJSONString(exchangeResult.getBody()));
            if (exchangeResult.getStatusCode().equals(HttpStatus.OK)){
                return exchangeResult.getBody();
            }else {
                return null;
            }
        }catch (HttpClientErrorException ex){
            ex.printStackTrace();
            return null;
        }
    }


    public boolean deleteRoute(String routeId){
        return delete(routeId, routeUrl);
    }

    private boolean delete(String id, String frontUrl) {
        try {
            String url = frontUrl +"/"+id;
            restTemplate.delete(url);
            return true;
        }catch (HttpClientErrorException ex){
            ex.printStackTrace();
            return false;
        }
    }

    private void setUp(){
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60000);
        requestFactory.setReadTimeout(20000);
        restTemplate.setRequestFactory(requestFactory);
    }


}
