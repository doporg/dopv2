package com.clsaa.dop.server.baas.Service;

import com.clsaa.dop.server.baas.model.yamlMo.Orderer;
import com.clsaa.dop.server.baas.model.yamlMo.Peer;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Service(value = "FabricNetOperateService")
public class FabricNetOperateService {
    @Autowired
    k8sClientService k8sClientService;
    @Autowired
    FabricK8sConfigMapGenerateService fabricK8sConfigMapGenerateService;
    @Autowired
    FabricK8sServiceGenerateService fabricK8sServiceGenerateService;
    @Autowired
    FabricK8sDeploymentGenerateService fabricK8sDeploymentGenerateService;

    /**
     * 创建命名空间
     */
    public V1Namespace createV1Namespace(String netName) throws IOException, ApiException {
        k8sClientService.setK8sClient();
        CoreV1Api apiInstance = new CoreV1Api();
        V1ObjectMeta om = new V1ObjectMeta().name(netName);
        V1Namespace namespace = new V1Namespace().apiVersion("apps/v1").kind("Namespace").metadata(om);
        try {
            V1Namespace result = apiInstance.createNamespace(namespace,null,null,null);
//            System.out.println(result);
            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#createNamespace");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 创建ConfigMap
     * */
    public V1ConfigMap createV1ConfigMap(String netName) throws IOException, ApiException {
        k8sClientService.setK8sClient();
        CoreV1Api apiInstance = new CoreV1Api();
        try {
            V1ConfigMap result = apiInstance.createNamespacedConfigMap(netName,fabricK8sConfigMapGenerateService.generateV1ConfigMap(netName),null,null,null);
//            System.out.println(result);
            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#createV1ConfigMap");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 创建PeerDeployment
     * */
    public V1Deployment createPeerDeployment(Peer peer, String orgName, String NameSpace) throws IOException, ApiException {
        k8sClientService.setK8sClient();
        AppsV1Api apiInstance = new AppsV1Api();
        try {
            V1Deployment result = apiInstance.createNamespacedDeployment(NameSpace,fabricK8sDeploymentGenerateService.generatePeerDeployment(peer,orgName,NameSpace),null,null,null);
//            System.out.println(result);
            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling AppsV1Api#createPeerDeployment");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 创建OrdererDeployment
     * */
    public V1Deployment createOrdererDeployment(String OrdererName,String NameSpace) throws IOException, ApiException {
        k8sClientService.setK8sClient();
        AppsV1Api apiInstance = new AppsV1Api();
        try {
            V1Deployment result = apiInstance.createNamespacedDeployment(NameSpace,fabricK8sDeploymentGenerateService.generateOrdererDeployment(OrdererName,NameSpace),null,null,null);
//            System.out.println(result);
            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling AppsV1Api#createOrdererDeployment");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 创建OrgcaDeployment
     * */
    public V1Deployment createOrgCaDeployment(String orgName,String NameSpace) throws IOException, ApiException {
        k8sClientService.setK8sClient();
        AppsV1Api apiInstance = new AppsV1Api();
        try {
            V1Deployment result = apiInstance.createNamespacedDeployment(NameSpace,fabricK8sDeploymentGenerateService.generateOrgCaDeployment(orgName,NameSpace),null,null,null);
//            System.out.println(result);
            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling AppsV1Api#createOrgCaDeployment");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 创建CliDeployment
     * */
    public V1Deployment createCliDeployment(String orgName,String NameSpace) throws IOException, ApiException {
        k8sClientService.setK8sClient();
        AppsV1Api apiInstance = new AppsV1Api();
        try {
            V1Deployment result = apiInstance.createNamespacedDeployment(NameSpace,fabricK8sDeploymentGenerateService.generateCliDeployment(orgName,NameSpace),null,null,null);
//            System.out.println(result);
            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling AppsV1Api#createCliDeployment");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 创建peerService
     * */
    public V1Service createPeerService(Peer peer, String orgName, String NameSpace) throws IOException, ApiException {
        k8sClientService.setK8sClient();
        CoreV1Api apiInstance = new CoreV1Api();
        try {
            V1Service result = apiInstance.createNamespacedService(NameSpace,fabricK8sServiceGenerateService.generatePeerService(peer,orgName,NameSpace),null,null,null);
            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#createPeerService");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }

    public V1Service createPeerMetricService(Peer peer, String orgName, String NameSpace) throws IOException, ApiException {
        k8sClientService.setK8sClient();
        CoreV1Api apiInstance = new CoreV1Api();
        try {
            V1Service result = apiInstance.createNamespacedService(NameSpace,fabricK8sServiceGenerateService.generatePeerMetricService(peer,orgName,NameSpace),null,null,null);
            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#createPeerService");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 创建OrdererService
     * */
    public V1Service createOrdererService(Orderer orderer, String NameSpace) throws IOException, ApiException {
        k8sClientService.setK8sClient();
        CoreV1Api apiInstance = new CoreV1Api();
        try {
            V1Service result = apiInstance.createNamespacedService(NameSpace,fabricK8sServiceGenerateService.generateOrdererService(orderer,NameSpace),null,null,null);
            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#createOrdererService");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }
    public V1Service createOrdererMetricService(Orderer orderer, String NameSpace) throws IOException, ApiException {
        k8sClientService.setK8sClient();
        CoreV1Api apiInstance = new CoreV1Api();
        try {
            V1Service result = apiInstance.createNamespacedService(NameSpace,fabricK8sServiceGenerateService.generateOrdererMetricService(orderer,NameSpace),null,null,null);
            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#createOrdererService");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 创建CaService
     * */
    public V1Service createOrgCaService(String orgName, String NameSpace) throws IOException, ApiException {
        k8sClientService.setK8sClient();
        CoreV1Api apiInstance = new CoreV1Api();
        try {
            V1Service result = apiInstance.createNamespacedService(NameSpace,fabricK8sServiceGenerateService.generateCaService(orgName,NameSpace),null,null,null);
//            System.out.println(result);
            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#createCaService");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }
}
