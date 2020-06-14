package com.clsaa.dop.server.baas.Service;

import io.kubernetes.client.models.V1ConfigMap;
import io.kubernetes.client.models.V1ObjectMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Service(value = "FabricK8sConfigMapGenerateService")
public class FabricK8sConfigMapGenerateService {
    @Autowired
    FabricYamlGenerateService fabricYamlGenerateService;
    /**
     *创建configMap
     * */
    public V1ConfigMap generateV1ConfigMap(String NameSpace) throws IOException {
        String content =fabricYamlGenerateService.getFromYaml("src/main/resource/builders-config-template.yaml");
        Map<String,String> map = new HashMap<>();
        map.put("app",NameSpace);
        V1ObjectMeta om = new V1ObjectMeta().name("builder-config").namespace(NameSpace).labels(map);
        Map<String,String> contentMap = new HashMap<>();
        contentMap.put("core.yaml",content);
        return new V1ConfigMap().apiVersion("apps/v1").metadata(om).data(contentMap);
    }
}