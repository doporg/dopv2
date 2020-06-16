package com.clsaa.dop.server.baas.Service;

import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Deployment;
import io.kubernetes.client.models.V1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Service(value = "FabricChaincodeService")
public class FabricChaincodeService {
    @Autowired
    FabricK8sDeploymentGenerateService fabricK8sDeploymentGenerateService;
    @Autowired
    FabricK8sServiceGenerateService fabricK8sServiceGenerateService;
    @Autowired
    k8sClientService k8sClientService;
    @Autowired
    JenkinsService jenkinsService;
    public V1Deployment createChaincodeDeployment(String orgName, String NameSpace) throws IOException, ApiException {
        k8sClientService.setK8sClient();
        AppsV1Api apiInstance = new AppsV1Api();
        try {
            V1Deployment result = apiInstance.createNamespacedDeployment(NameSpace,fabricK8sDeploymentGenerateService.generateChaincodeDeployment(orgName,NameSpace),null,null,null);
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
    public V1Service createChaincodeService(String NameSpace, String orgName) throws IOException, ApiException {
        k8sClientService.setK8sClient();
        CoreV1Api apiInstance = new CoreV1Api();
        try {
            V1Service result = apiInstance.createNamespacedService(NameSpace,fabricK8sServiceGenerateService.generateChaincodeService(NameSpace,orgName),null,null,null);
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
    public void createChaincode(String Namespace, List<String> podList, String ChannelName, String git) throws IOException, ApiException {
        String job1 = jenkinsService.createChaincodeInCli(Namespace,podList.get(0),podList.get(1));
        String job2 = jenkinsService.createChaincodeInChannel(Namespace,podList.get(0),podList.get(1),podList.get(2),ChannelName,git);
        jenkinsService.buildJob(job1);
        this.createChaincodeDeployment("org1",Namespace);
        this.createChaincodeService("org1",Namespace);
        this.createChaincodeDeployment("org2",Namespace);
        this.createChaincodeService("org2",Namespace);
        jenkinsService.buildJob(job2);
    }

}