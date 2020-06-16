package com.clsaa.dop.server.baas.Service;

import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Service(value = "FabricK8sQueryService")
public class FabricK8sQueryService {
    @Autowired
    k8sClientService k8sClientService;
    /**
     * 接收节点信息，就是kubectl get pods -n hyperledger的那些
     * */
    public Map<String,String> getNameSpacePodListStatu(String NameSpace) throws IOException, ApiException {
        k8sClientService.setK8sClient();
        CoreV1Api apiInstance = new CoreV1Api();
        String namespace = "hyperledger";
        try {
            V1PodList result = apiInstance.listNamespacedPod(namespace, true, null, null, null, null, null, null, null, null);
            List<V1Pod> list = result.getItems();
            Map<String,String> map = new HashMap<>();
            for(V1Pod s:list){
                map.put(s.getMetadata().getName(),s.getStatus().getPhase());
            }
            return map;
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#getNameSpacePodListStatu");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }

}