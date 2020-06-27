package com.clsaa.dop.server.baas.Service;

import com.clsaa.dop.server.baas.model.yamlMo.Orderer;
import com.clsaa.dop.server.baas.model.yamlMo.Peer;
import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.models.V1Service;
import io.kubernetes.client.models.V1ServicePort;
import io.kubernetes.client.models.V1ServiceSpec;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Service(value = "FabricK8sServiceGenerate")
public class FabricK8sServiceGenerateService {
    /**
     * 生成Oreder Service
     * */
    public V1Service generateOrdererService(Orderer orderer, String NameSpace){
        Map<String,String> map = new HashMap<>();
        map.put("app",orderer.getOrderName());
        V1ObjectMeta om = new V1ObjectMeta().labels(map).name(orderer.getOrderName()).namespace(NameSpace);
        List<V1ServicePort> spList = new ArrayList<>();
        V1ServicePort sp = new V1ServicePort().name(orderer.getOrderName()).port(Integer.parseInt(orderer.getOrderport())).targetPort(new IntOrString(Integer.parseInt(orderer.getOrderport())));
        spList.add(sp);
        V1ServiceSpec ss = new V1ServiceSpec().type("ClusterIP").ports(spList).selector(map);
        return new V1Service().apiVersion("v1").kind("Service").metadata(om).spec(ss);
    }
    /**
     * 生成Oreder Metric Service
     * */
    public V1Service generateOrdererMetricService(Orderer orderer,String NameSpace){
        Map<String,String> map = new HashMap<>();
        map.put("app",orderer.getOrderName());
        map.put("metrics-service","true");
        V1ObjectMeta om = new V1ObjectMeta().labels(map).name(orderer.getOrderName()+"-metrics").namespace(NameSpace);
        List<V1ServicePort> spList = new ArrayList<>();
        V1ServicePort sp = new V1ServicePort().name("orderer-metrics").port(8443).targetPort(new IntOrString(8443));
        spList.add(sp);
        V1ServiceSpec ss = new V1ServiceSpec().type("ClusterIP").ports(spList).selector(map);
        return new V1Service().apiVersion("v1").kind("Service").metadata(om).spec(ss);
    }
    /**
     * 生成Ca Service
     * */
    public V1Service generateCaService(String orgName,String NameSpace){
        Map<String,String> map = new HashMap<>();
        map.put("app:","ca-"+orgName);
        V1ObjectMeta om = new V1ObjectMeta().labels(map).name("ca-"+orgName).namespace(NameSpace);
        List<V1ServicePort> spList = new ArrayList<>();
        V1ServicePort sp = new V1ServicePort().name("https").port(7054).targetPort(new IntOrString(7054));
        spList.add(sp);
        V1ServiceSpec ss = new V1ServiceSpec().type("ClusterIP").ports(spList).selector(map);
        return new V1Service().apiVersion("v1").kind("Service").metadata(om).spec(ss);
    }
    /**
     * 生成Peer Service
     * */
    public V1Service generatePeerService(Peer peer, String orgName, String NameSpace){
        Map<String,String> map = new HashMap<>();
        map.put("app:",peer.getPeerName());
        V1ObjectMeta om = new V1ObjectMeta().labels(map).name(peer.getPeerName()).namespace(NameSpace);
        List<V1ServicePort> spList = new ArrayList<>();
        V1ServicePort sp = new V1ServicePort().name("peer-core").port(7051).targetPort(new IntOrString(7051));
        spList.add(sp);
        V1ServiceSpec ss = new V1ServiceSpec().type("ClusterIP").ports(spList).selector(map);
        return new V1Service().apiVersion("v1").kind("Service").metadata(om).spec(ss);
    }
    /**
     * 生成Peer Metric Service
     * */
    public V1Service generatePeerMetricService(Peer peer,String orgName,String NameSpace){
        Map<String,String> map = new HashMap<>();
        map.put("app",peer.getPeerName());
        map.put("metrics-service","true");
        V1ObjectMeta om = new V1ObjectMeta().labels(map).name(peer.getPeerName()+"-metrics").namespace(NameSpace);
        List<V1ServicePort> spList = new ArrayList<>();
        V1ServicePort sp = new V1ServicePort().name("peer-metrics").port(9443).targetPort(new IntOrString(9443));
        spList.add(sp);
        V1ServiceSpec ss = new V1ServiceSpec().type("ClusterIP").ports(spList).selector(map);
        return new V1Service().apiVersion("v1").kind("Service").metadata(om).spec(ss);
    }

    /**
     * 生成chaincode Service
     * */
    public V1Service generateChaincodeService(String NameSpace,String orgName){
        Map<String,String> map = new HashMap<>();
        map.put("app","chaincode-marbles-"+orgName);
        V1ObjectMeta om = new V1ObjectMeta().labels(map).name("chaincode-marbles-"+orgName).namespace(NameSpace);
        V1ServicePort sp = new V1ServicePort().name("grpc").port(7052).targetPort(new IntOrString(7052));
        List<V1ServicePort> spList = new ArrayList<>();
        spList.add(sp);
        V1ServiceSpec ss = new V1ServiceSpec().ports(spList).selector(map);
        return new V1Service().apiVersion("v1").kind("Service").metadata(om).spec(ss);
    }
}
