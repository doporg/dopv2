package com.clsaa.dop.server.api.restTemplate;

//import com.alibaba.fastjson.JSON;
import com.clsaa.dop.server.api.module.kong.pluginModule.KongPlugin;
import com.clsaa.dop.server.api.module.kong.routeModule.KongRoute;
import com.clsaa.dop.server.api.module.kong.serviceModule.KongService;
import com.clsaa.dop.server.api.module.kong.serviceModule.KongServiceList;
import com.clsaa.dop.server.api.module.kong.targetModule.KongTarget;
import com.clsaa.dop.server.api.module.kong.upstreamModule.KongUpstream;
import com.clsaa.dop.server.api.module.request.lifeCycle.FusePolicy;
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
    private static String upstreamUrl;
    private static  String pluginUrl;

    public ApiRestTemplate() {
        this.restTemplate = new RestTemplate();
    }

    @Value("${api.kong.url}")
    public void setTargetUrl(String url) {
        ApiRestTemplate.serviceUrl = url+"/services";
        ApiRestTemplate.routeUrl = url+"/routes";
        ApiRestTemplate.upstreamUrl = url+"/upstreams";
        ApiRestTemplate.pluginUrl = url+"/plugins";
    }

    public KongServiceList getRequest(){
         return restTemplate.getForObject(serviceUrl,KongServiceList.class);
    }

    public KongService createService(String host,String name,Long timeout,String protocol,Long port,String path){
        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE,(MediaType.MULTIPART_FORM_DATA_VALUE));

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("host", host);
        map.add("name", name);
        map.add("port", port);
        map.add("path", path);
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

    public KongService modifyService(String serviceId,String host,String name,Long timeout,String protocol,Long port,String path){
        setUp();

        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE,(MediaType.MULTIPART_FORM_DATA_VALUE));
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("host", host);
        map.add("name", name);
        map.add("port", port);
        map.add("path", path);
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
        setUp();
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

    public KongUpstream createUpstream(String name, String algorithm, String hash_on, String hash_on_header){
        setUp();
        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE,(MediaType.MULTIPART_FORM_DATA_VALUE));

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("name", name);
        map.add("algorithm", algorithm);
        if (algorithm.equals("hash")){
            map.add("hash_on", hash_on);
            if (hash_on.equals("header")){
                map.add("hash_on_header",hash_on_header);
            }
        }

        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, header);
        try {
            ResponseEntity<KongUpstream> exchangeResult = restTemplate.exchange(upstreamUrl, HttpMethod.POST, request, KongUpstream.class);
            if (exchangeResult.getStatusCode().equals(HttpStatus.CREATED)){
                return exchangeResult.getBody();
            }else {
                return null;
            }
        }catch (HttpClientErrorException ex){
            ex.printStackTrace();
            //System.out.println(name+" "+algorithm+" "+hash_on+" "+hash_on_header);
            return null;
        }
    }

    public KongUpstream createUpstream(String name){
        setUp();
        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE,(MediaType.MULTIPART_FORM_DATA_VALUE));

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("name", name);

        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, header);
        try {
            ResponseEntity<KongUpstream> exchangeResult = restTemplate.exchange(upstreamUrl, HttpMethod.POST, request, KongUpstream.class);
            if (exchangeResult.getStatusCode().equals(HttpStatus.CREATED)){
                return exchangeResult.getBody();
            }
        }catch (HttpClientErrorException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public KongUpstream modifyUpstream(String upstreamId, String name){
        setUp();

        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE,(MediaType.MULTIPART_FORM_DATA_VALUE));

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("name", name);
        String url = upstreamUrl+"/"+upstreamId;

        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, header);
        try {
            ResponseEntity<KongUpstream> exchangeResult = restTemplate.exchange(url, HttpMethod.PATCH, request, KongUpstream.class);
            if (exchangeResult.getStatusCode().equals(HttpStatus.OK)){
                return exchangeResult.getBody();
            }
        }catch (HttpClientErrorException ex){
            ex.printStackTrace();

        }
        return null;
    }

    public KongUpstream modifyFusePolicy(String name,FusePolicy fusePolicy){
        setUp();

        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE,(MediaType.MULTIPART_FORM_DATA_VALUE));

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        if (fusePolicy.isEnable()){
            int failures = fusePolicy.getCriticalFusingFailureRate().intValue()*fusePolicy.getFuseDetectionRing().intValue()/100;
            map.add("healthchecks.passive.unhealthy.timeouts", failures);
            map.add("healthchecks.passive.unhealthy.http_failures", failures);
            map.add("healthchecks.passive.unhealthy.tcp_failures", failures);
            map.add("healthchecks.active.healthy.successes", fusePolicy.getReplyDetectionRingSize());
            map.add("healthchecks.active.unhealthy.interval", fusePolicy.getFuseDuration());
        }else {
            map.add("healthchecks.passive.unhealthy.timeouts", 0);
            map.add("healthchecks.passive.unhealthy.http_failures", 0);
            map.add("healthchecks.passive.unhealthy.tcp_failures", 0);
            map.add("healthchecks.active.healthy.successes", 0);
            map.add("healthchecks.active.unhealthy.interval", 0);
        }
        String url = upstreamUrl+"/"+name;

        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, header);
        try {
            ResponseEntity<KongUpstream> exchangeResult = restTemplate.exchange(url, HttpMethod.PATCH, request, KongUpstream.class);
            if (exchangeResult.getStatusCode().equals(HttpStatus.OK)){
                return exchangeResult.getBody();
            }
        }catch (HttpClientErrorException ex){
            ex.printStackTrace();

        }
        return null;
    }

    public KongUpstream modifyUpstream(String upstreamId, String algorithm, String hash_on, String hash_on_header){
        setUp();

        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE,(MediaType.MULTIPART_FORM_DATA_VALUE));

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("algorithm", algorithm);
        if (algorithm.equals("hash")){
            map.add("hash_on", hash_on);
            if (hash_on.equals("header")){
                map.add("hash_on_header",hash_on_header);
            }
        }
        String url = upstreamUrl+"/"+upstreamId;

        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, header);
        try {
            ResponseEntity<KongUpstream> exchangeResult = restTemplate.exchange(url, HttpMethod.PATCH, request, KongUpstream.class);
            if (exchangeResult.getStatusCode().equals(HttpStatus.OK)){
                return exchangeResult.getBody();
            }else {
                //System.out.println(exchangeResult.getStatusCode()+" "+JSON.toJSONString(exchangeResult.getBody()));
                return null;
            }
        }catch (HttpClientErrorException ex){
            ex.printStackTrace();
            //System.out.println(name+" "+algorithm+" "+hash_on+" "+hash_on_header);
            return null;
        }
    }

    public KongTarget createTarget(String upstreamId,String target){
        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE,(MediaType.MULTIPART_FORM_DATA_VALUE));

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("target", target);
        map.add("weight", 100);

        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, header);
        try {
            ResponseEntity<KongTarget> exchangeResult = restTemplate.exchange(getTargetUrl(upstreamId), HttpMethod.POST, request, KongTarget.class);
            if (exchangeResult.getStatusCode().equals(HttpStatus.CREATED)){
                return exchangeResult.getBody();
            }
        }catch (HttpClientErrorException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public KongTarget createTarget(String upstreamId,String target,int weight){
        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE,(MediaType.MULTIPART_FORM_DATA_VALUE));

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("target", target);
        map.add("weight", weight);

        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, header);
        try {
            ResponseEntity<KongTarget> exchangeResult = restTemplate.exchange(getTargetUrl(upstreamId), HttpMethod.POST, request, KongTarget.class);
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

    public String createCurrentLimitPolicy(String serviceId,int second,int minute,int hour,int day){
        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE,(MediaType.MULTIPART_FORM_DATA_VALUE));

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        map.add("name", "rate-limiting");
        map.add("service.id", serviceId);
        if(second!=0){
            map.add("config.second", second);
        }
        if(minute!=0){
            map.add("config.minute", minute);
        }
        if(hour!=0){
            map.add("config.hour", hour);
        }
        if(day!=0){
            map.add("config.day", day);
        }

        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, header);
        try {
            ResponseEntity<KongPlugin> exchangeResult = restTemplate.exchange(pluginUrl, HttpMethod.POST, request, KongPlugin.class);
            if (exchangeResult.getStatusCode().equals(HttpStatus.CREATED)&&exchangeResult.getBody()!=null){
                return exchangeResult.getBody().getId();
            }else {
                return null;
            }
        }catch (HttpClientErrorException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public boolean updateCurrentLimitPolicy(String pluginId,String serviceId,int second,int minute,int hour,int day){
        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE,(MediaType.MULTIPART_FORM_DATA_VALUE));

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        map.add("name", "rate-limiting");
        map.add("service.id", serviceId);
        if(second!=0){
            map.add("config.second", second);
        }
        if(minute!=0){
            map.add("config.minute", minute);
        }
        if(hour!=0){
            map.add("config.hour", hour);
        }
        if(day!=0){
            map.add("config.day", day);
        }
        String url = pluginUrl+"/"+pluginId;
        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, header);
        try {
            ResponseEntity<KongPlugin> exchangeResult = restTemplate.exchange(url, HttpMethod.PATCH, request, KongPlugin.class);
            return exchangeResult.getStatusCode().equals(HttpStatus.OK) && exchangeResult.getBody() != null;
        }catch (HttpClientErrorException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteUpstream(String upstreamId){
        return delete(upstreamId, upstreamUrl);
    }

    public void deleteTarget(String upstreamId,String targetId){
       delete(upstreamId+"/targets/"+targetId, upstreamUrl);
    }

    public void deletePlugin(String pluginId){
        delete(pluginId, pluginUrl);
    }

    private void setUp(){
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60000);
        requestFactory.setReadTimeout(20000);
        restTemplate.setRequestFactory(requestFactory);
    }

    private String getTargetUrl(String upstreamId){
        return upstreamUrl+"/"+upstreamId+"/targets";
    }


}
