package com.clsaa.dop.server.baas.Service;

import com.clsaa.dop.server.baas.model.yamlMo.Peer;
import io.kubernetes.client.models.*;
import org.springframework.stereotype.Service;
import com.clsaa.dop.server.baas.model.yamlMo.Orderer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Service(value ="FabricK8sDeploymentGenerateService" )
public class FabricK8sDeploymentGenerateService {
    /**
     * 生成peer的V1Deployment
     **/
    public V1Deployment generatePeerDeployment(Peer peer, String orgName, String NameSpace){
        Map<String,String> map = new HashMap<>();
        map.put("app",peer.getPeerName());
        V1ObjectMeta om  = new V1ObjectMeta().name(peer.getPeerName()).namespace(NameSpace).labels(map);
        return new V1Deployment().apiVersion("apps/v1")
                .kind("Deployment")
                .metadata(om)
                .spec(genetatePeerFirstSpec(orgName,peer,NameSpace));
    }
    /**
     * 生成orderer的V1Deployment
     **/
    public V1Deployment generateOrdererDeployment(String OrdererName,String NameSpace){
        Map<String,String> map = new HashMap<>();
        map.put("app",OrdererName);
        V1ObjectMeta om  = new V1ObjectMeta().name(OrdererName).namespace(NameSpace).labels(map);
        return new V1Deployment().apiVersion("apps/v1")
                .kind("Deployment")
                .metadata(om)
                .spec(genetateOrdererFirstSpec(OrdererName,NameSpace));
    }
    /**
     * 生成Org ca的V1Deployment
     * */
    public V1Deployment generateOrgCaDeployment(String orgName,String NameSpace){
        Map<String,String> map = new HashMap<>();
        map.put("app","ca-"+orgName);
        V1ObjectMeta om  = new V1ObjectMeta().name("ca-"+orgName).namespace(NameSpace).labels(map);
        return new V1Deployment().apiVersion("apps/v1")
                .kind("Deployment")
                .metadata(om)
                .spec(genetateCaFirstSpec(orgName,NameSpace));
    }
    /**
     * 生成cli的V1Deployment
     * */
    public V1Deployment generateCliDeployment(String orgName,String NameSpace){
        Map<String,String> map = new HashMap<>();
        map.put("app","cli-"+orgName);
        V1ObjectMeta om  = new V1ObjectMeta().name("cli-"+orgName).namespace(NameSpace).labels(map);
        return new V1Deployment().apiVersion("apps/v1")
                .kind("Deployment")
                .metadata(om)
                .spec(generateCliFirstSpec(orgName,NameSpace));
    }
    /**
     * 生成Chaincode的V1Deployment
     * */
    public V1Deployment generateChaincodeDeployment(String orgName,String NameSpace){
        Map<String,String> map = new HashMap<>();
        map.put("app","chaincode-marbles-"+orgName);
        V1ObjectMeta om  = new V1ObjectMeta().name("chaincode-marbles-"+orgName).namespace(NameSpace).labels(map);
        return new V1Deployment().apiVersion("apps/v1")
                .kind("Deployment")
                .metadata(om)
                .spec(generateChaincodeFirstSpec(orgName,NameSpace));
    }
    /**
     * 生成peer Deployment spec模块
     * */
    public V1DeploymentSpec genetatePeerFirstSpec(String OrgName, Peer peer, String netName){
        Map<String,String> map = new HashMap<>();
        map.put("app",peer.getPeerName());
        V1LabelSelector ls = new V1LabelSelector().matchLabels(map);
        V1DeploymentStrategy ds = new V1DeploymentStrategy().type("Recreate");
        V1DeploymentSpec dps = new V1DeploymentSpec().selector(ls).replicas(1).strategy(ds).template(generatePeerTemplate(peer,OrgName,netName));
        return dps;
    }
    /**
     * 生成orderer Deployment spec模块
     * */
    public V1DeploymentSpec genetateOrdererFirstSpec(String OrdererName,String netName){
        Map<String,String> map = new HashMap<>();
        map.put("app",OrdererName);
        V1LabelSelector ls = new V1LabelSelector().matchLabels(map);
        V1DeploymentStrategy ds = new V1DeploymentStrategy().type("Recreate");
        V1DeploymentSpec dps = new V1DeploymentSpec().selector(ls).replicas(1).strategy(ds).template(generateOrdererTemplate(OrdererName,netName));
        return dps;
    }
    /**
     * 生成ca Deployment spec模块
     * */
    public V1DeploymentSpec genetateCaFirstSpec(String orgName,String netName){
        Map<String,String> map = new HashMap<>();
        map.put("app","ca-"+orgName);
        V1LabelSelector ls = new V1LabelSelector().matchLabels(map);
        V1DeploymentStrategy ds = new V1DeploymentStrategy().type("Recreate");
        V1DeploymentSpec dps = new V1DeploymentSpec().selector(ls).replicas(1).strategy(ds).template(generateCaTemplate(orgName,netName));
        return dps;
    }
    /**
     * 生成cli Deployment spec模块
     * */
    public V1DeploymentSpec generateCliFirstSpec(String orgName,String netName){
        Map<String,String> map = new HashMap<>();
        map.put("app","cli-"+orgName);
        V1LabelSelector ls = new V1LabelSelector().matchLabels(map);
        V1DeploymentStrategy ds = new V1DeploymentStrategy().type("Recreate");
        V1DeploymentSpec dps = new V1DeploymentSpec().selector(ls).replicas(1).strategy(ds).template(generateCliTemplate(orgName,netName));
        return dps;
    }
    /**
     * 生成chaincode Deployment spec模块
     * */
    public V1DeploymentSpec generateChaincodeFirstSpec(String orgName,String netName){
        Map<String,String> map = new HashMap<>();
        map.put("app","chaincode-marbles-"+orgName);
        V1LabelSelector ls = new V1LabelSelector().matchLabels(map);
        V1DeploymentStrategy ds = new V1DeploymentStrategy().type("Recreate");
        V1DeploymentSpec dps = new V1DeploymentSpec().selector(ls).replicas(3).strategy(ds).template(generateChaincodeTemplate(orgName,netName));
        return dps;
    }
    /**
     * 生成peer Deployment spec中spec的container模块
     * */
    public List<V1Container> generatePeerSecondSpecContainer(String orgName, Peer peer){
        List<String> portList = peer.getPorts();
        int portSize = portList.size();
        List<String> argsList = new ArrayList<>();
        argsList.add("peer");argsList.add("node");argsList.add("start");
        List<V1ContainerPort> v1PortList = new ArrayList<>();
        for(int i=0;i<portSize;i++){
            V1ContainerPort p1 = new V1ContainerPort().containerPort(Integer.parseInt(portList.get(i)));
            v1PortList.add(p1);
        }
        List<V1EnvVar> envList = new ArrayList<>();
        V1EnvVar v1 = new V1EnvVar().name("FABRIC_LOGGING_SPEC").value("INFO");envList.add(v1);
        V1EnvVar v2 = new V1EnvVar().name("CORE_PEER_ADDRESS").value(peer.getPeerName()+":7051");envList.add(v2);
        V1EnvVar v3 = new V1EnvVar().name("CORE_PEER_GOSSIP_EXTERNALENDPOINT").value(peer.getPeerName()+":7051");envList.add(v3);
        V1EnvVar v4 = new V1EnvVar().name("CORE_PEER_CHAINCODELISTENADDRESS").value(peer.getPeerName()+":7052");envList.add(v4);
        V1EnvVar v5 = new V1EnvVar().name("CORE_PEER_GOSSIP_USELEADERELECTION").value("true");envList.add(v5);
        V1EnvVar v6 = new V1EnvVar().name("CORE_PEER_GOSSIP_BOOTSTRAP").value(peer.getPeerName()+":7051");envList.add(v6);
        V1EnvVar v7 = new V1EnvVar().name("CORE_PEER_ID").value(peer.getPeerName());envList.add(v7);
        V1EnvVar v8 = new V1EnvVar().name("CORE_PEER_LOCALMSPID").value(orgName+"MSP");envList.add(v8);
        V1EnvVar v9 = new V1EnvVar().name("CORE_PEER_PROFILE_ENABLED").value("true");envList.add(v9);
        V1EnvVar v10 = new V1EnvVar().name("CORE_PEER_TLS_CERT_FILE").value("/etc/hyperledger/fabric/tls/server.crt");envList.add(v10);
        V1EnvVar v11 = new V1EnvVar().name("CORE_PEER_TLS_ENABLED").value("true");envList.add(v11);
        V1EnvVar v12 = new V1EnvVar().name("CORE_PEER_TLS_KEY_FILE").value("/etc/hyperledger/fabric/tls/server.key");envList.add(v12);
        V1EnvVar v13 = new V1EnvVar().name("CORE_PEER_TLS_ROOTCERT_FILE").value("/etc/hyperledger/fabric/tls/ca.crt");envList.add(v13);
        V1EnvVar v14 = new V1EnvVar().name("CORE_VM_ENDPOINT").value("http://localhost:2375");envList.add(v14);
        V1EnvVar v15 = new V1EnvVar().name("CORE_OPERATIONS_LISTENADDRESS").value("0.0.0.0:9443");envList.add(v15);
        V1EnvVar v16 = new V1EnvVar().name("CORE_METRICS_PROVIDER").value("prometheus");envList.add(v16);
        List<V1VolumeMount> voList = new ArrayList<>();
        V1VolumeMount vm1 = new V1VolumeMount().mountPath("/host/var/run/").name(peer.getPeerName()+"-claim0");voList.add(vm1);
        V1VolumeMount vm2 = new V1VolumeMount().mountPath("/etc/hyperledger/fabric/msp").name(peer.getPeerName()+"-claim1");voList.add(vm2);
        V1VolumeMount vm3 = new V1VolumeMount().mountPath("/etc/hyperledger/fabric/tls").name(peer.getPeerName()+"-claim2");voList.add(vm3);
        V1VolumeMount vm4 = new V1VolumeMount().mountPath("/var/hyperledger/production").name(peer.getPeerName().split("-")[0]+"-persistentdata");voList.add(vm4);
        V1VolumeMount vm5 = new V1VolumeMount().mountPath("/etc/hyperledger/fabric/core.yaml").name("builders-config").subPath("core.yaml");voList.add(vm5);
        V1VolumeMount vm6 = new V1VolumeMount().mountPath("/builders/external").name("external-builder");voList.add(vm6);
        V1Container OrdererServiceContainer = new V1Container().env(envList)
                .image("hyperledger/fabric-peer:amd64-2.1.0")
                .name(peer.getPeerName())
                .workingDir("/root/go/src/github.com/hyperledger/fabric/peer")
                .ports(v1PortList)
                .volumeMounts(voList);
        List<V1Container> cList = new ArrayList<>();cList.add(OrdererServiceContainer);
        return cList;
    }
    /**
     * 生成org cli Deployment spec中spec的container模块
     * */
    public List<V1Container> generateCliSecondSpecContainer(String orgName){
        List<V1EnvVar> envList = new ArrayList<>();
        V1EnvVar v1 = new V1EnvVar().name("FABRIC_LOGGING_SPEC").value("INFO");envList.add(v1);
        V1EnvVar v2 = new V1EnvVar().name("CORE_PEER_ADDRESS").value("peer0-"+orgName+":7051");envList.add(v2);
        V1EnvVar v3 = new V1EnvVar().name("CORE_PEER_ID").value("cli-"+orgName);envList.add(v3);
        V1EnvVar v4 = new V1EnvVar().name("CORE_PEER_LOCALMSPID").value(orgName+"MSP");envList.add(v4);
        V1EnvVar v5 = new V1EnvVar().name("CORE_PEER_MSPCONFIGPATH").value("/root/go/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/"+orgName+"/users/Admin@"+orgName+"/msp");envList.add(v5);
        V1EnvVar v6 = new V1EnvVar().name("CORE_PEER_TLS_CERT_FILE").value("/root/go/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/"+orgName+"+/peers/peer0-"+orgName+"/tls/server.crt");envList.add(v6);
        V1EnvVar v7 = new V1EnvVar().name("CORE_PEER_TLS_ENABLED").value("\"true\"");envList.add(v7);
        V1EnvVar v8 = new V1EnvVar().name("CORE_PEER_TLS_KEY_FILE").value("/root/go/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/"+orgName+"/peers/peer0-"+orgName+"/tls/server.key");envList.add(v8);
        V1EnvVar v9 = new V1EnvVar().name("CORE_PEER_TLS_ROOTCERT_FILE").value("/root/go/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/"+orgName+"/peers/peer0-"+orgName+"/tls/ca.crt");envList.add(v9);
        V1EnvVar v10 = new V1EnvVar().name("GOPATH").value("/root/go");envList.add(v10);
        V1EnvVar v11 = new V1EnvVar().name("ORDERER_CA").value("/root/go/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/consortium/orderers/orderer0/msp/tlscacerts/tlsca.consortium-cert.pem");envList.add(v11);
        List<V1VolumeMount> voList = new ArrayList<>();
        V1VolumeMount vm1 = new V1VolumeMount().mountPath("/host/var/run/").name("ca-claim0");voList.add(vm1);
        V1VolumeMount vm2 = new V1VolumeMount().mountPath("/root/go/src/github.com/marbles/").name("ca-claim1");voList.add(vm2);
        V1VolumeMount vm3 = new V1VolumeMount().mountPath("/root/go/src/github.com/hyperledger/fabric/peer/scripts/channel-artifacts").name("ca-claim2");voList.add(vm3);
        V1VolumeMount vm4 = new V1VolumeMount().mountPath("/root/go/src/github.com/hyperledger/fabric/peer/crypto/").name("ca-claim3");voList.add(vm4);
        V1Container OrdererServiceContainer = new V1Container().env(envList)
                .image("hyperledger/fabric-tools:amd64-2.1.0")
                .name("cli")
                .tty(true)
                .workingDir("/root/go/src/github.com/hyperledger/fabric/peer")
                .volumeMounts(voList);
        List<V1Container> cList = new ArrayList<>();cList.add(OrdererServiceContainer);
        return cList;
    }
    /**
     * 生成org ca Deployment spec中spec的container模块
     * */
    public List<V1Container> generateCaSecondSpecContainer(String orgName){
        List<String> argsList = new ArrayList<>();
        argsList.add("sh");argsList.add("-c");argsList.add("fabric-ca-server start --ca.certfile /etc/hyperledger/fabric-ca-server-config/ca.org1-cert.pem --ca.keyfile /etc/hyperledger/fabric-ca-server-config/priv_sk -b admin:adminpw -d");
        List<V1EnvVar> envList = new ArrayList<>();
        V1EnvVar v1 = new V1EnvVar().name("FABRIC_CA_HOME").value("/etc/hyperledger/fabric-ca-server");envList.add(v1);
        V1EnvVar v2 = new V1EnvVar().name("FABRIC_CA_SERVER_CA_NAME").value("ca-"+orgName);envList.add(v2);
        V1EnvVar v3 = new V1EnvVar().name("FABRIC_CA_SERVER_TLS_CERTFILE").value("/etc/hyperledger/fabric-ca-server-config/ca."+orgName+"-cert.pem");envList.add(v3);
        V1EnvVar v4 = new V1EnvVar().name("FABRIC_CA_SERVER_TLS_ENABLED").value("true");envList.add(v4);
        V1EnvVar v5 = new V1EnvVar().name("FABRIC_CA_SERVER_TLS_KEYFILE").value("/etc/hyperledger/fabric-ca-server-config/priv_sk");envList.add(v5);
        List<V1VolumeMount> voList = new ArrayList<>();
        V1VolumeMount vm1 = new V1VolumeMount().mountPath("/etc/hyperledger/fabric-ca-server-config").name("ca-"+orgName+"-claim0").readOnly(true);voList.add(vm1);
        List<V1ContainerPort> portList = new ArrayList<>();
        V1ContainerPort p1 = new V1ContainerPort().containerPort(7054);portList.add(p1);
        V1Container OrdererServiceContainer = new V1Container().args(argsList)
                .env(envList)
                .image("hyperledger/fabric-ca:amd64-1.4.6")
                .name("ca-"+orgName)
                .ports(portList)
                .volumeMounts(voList);
        List<V1Container> cList = new ArrayList<>();cList.add(OrdererServiceContainer);
        return cList;
    }
    /**
     * 生成Chaincode Deployment spec中spec的container模块
     * */
    public List<V1Container> generateChaincodeSecondSpecContainer(String orgName){
        List<V1EnvVar> envList = new ArrayList<>();
        V1EnvVar v1 = new V1EnvVar().name("CHAINCODE_CCID").value("registry.dop.clsaa.com/chaincode/marbles:1.0");envList.add(v1);
        V1EnvVar v2 = new V1EnvVar().name("CHAINCODE_ADDRESS").value("0.0.0.0:7052");envList.add(v2);
        List<V1ContainerPort> portList = new ArrayList<>();
        V1ContainerPort p1 = new V1ContainerPort().containerPort(7052);portList.add(p1);
        V1Container OrdererServiceContainer = new V1Container().image("registry.dop.clsaa.com/chaincode/marbles:1.0")
                .env(envList)
                .name("chaincode-marbles-"+orgName)
                .ports(portList)
                .imagePullPolicy("IfNotPresent");
        List<V1Container> cList = new ArrayList<>();cList.add(OrdererServiceContainer);
        return cList;
    }
    /**
     * 生成orderer Deployment spec中spec的container模块
     * */
    public List<V1Container> generateOrdererSecondSpecContainer(String OrdererName){
        List<V1EnvVar> EnvList = new ArrayList<>();
        V1EnvVar v1 = new V1EnvVar().name("FABRIC_LOGGING_SPEC").value("DEBUG");EnvList.add(v1);
        V1EnvVar v2 = new V1EnvVar().name("ORDERER_OPERATIONS_LISTENADDRESS").value("0.0.0.0:8443");EnvList.add(v2);
        V1EnvVar v3 = new V1EnvVar().name("ORDERER_METRICS_PROVIDER").value("prometheus");EnvList.add(v3);
        V1EnvVar v4 = new V1EnvVar().name("ORDERER_GENERAL_GENESISFILE").value("/var/hyperledger/orderer/genesis.block");EnvList.add(v4);
        V1EnvVar v5 = new V1EnvVar().name("ORDERER_GENERAL_GENESISMETHOD").value("file");EnvList.add(v5);
        V1EnvVar v6 = new V1EnvVar().name("ORDERER_GENERAL_LISTENADDRESS").value("0.0.0.0");EnvList.add(v6);
        V1EnvVar v7 = new V1EnvVar().name("ORDERER_GENERAL_LOCALMSPDIR").value("/var/hyperledger/orderer/msp");EnvList.add(v7);
        V1EnvVar v8 = new V1EnvVar().name("ORDERER_GENERAL_LOCALMSPID").value("OrdererMSP");EnvList.add(v8);
        V1EnvVar v9 = new V1EnvVar().name("ORDERER_GENERAL_TLS_CERTIFICATE").value("/var/hyperledger/orderer/tls/server.crt");EnvList.add(v9);
        V1EnvVar v10 = new V1EnvVar().name("ORDERER_GENERAL_TLS_ENABLED").value("true");EnvList.add(v10);
        V1EnvVar v11 = new V1EnvVar().name("ORDERER_GENERAL_TLS_PRIVATEKE").value("/var/hyperledger/orderer/tls/server.key");EnvList.add(v11);
        V1EnvVar v12 = new V1EnvVar().name("ORDERER_GENERAL_TLS_ROOTCAS").value("\"[/var/hyperledger/orderer/tls/ca.crt]\"");EnvList.add(v12);
        V1EnvVar v13 = new V1EnvVar().name("ORDERER_GENERAL_CLUSTER_CLIENTCERTIFICATE").value("/var/hyperledger/orderer/tls/server.crt");EnvList.add(v13);
        V1EnvVar v14 = new V1EnvVar().name("ORDERER_GENERAL_CLUSTER_CLIENTPRIVATEKEY").value("/var/hyperledger/orderer/tls/server.key");EnvList.add(v14);
        V1EnvVar v15 = new V1EnvVar().name("ORDERER_GENERAL_CLUSTER_ROOTCAS").value("\"[/var/hyperledger/orderer/tls/ca.crt]\"");EnvList.add(v15);
        List<String> argsList = new ArrayList<>();
        argsList.add("orderer");
        List<V1ContainerPort> portList = new ArrayList<>();
        V1ContainerPort p1 = new V1ContainerPort().containerPort(7050);portList.add(p1);
        V1ContainerPort p2 = new V1ContainerPort().containerPort(8443);portList.add(p2);
        List<V1VolumeMount> voList = new ArrayList<>();
        V1VolumeMount vm1 = new V1VolumeMount().mountPath("/var/hyperledger/production").name(OrdererName+"-persistentdata");voList.add(vm1);
        V1VolumeMount vm2 = new V1VolumeMount().mountPath("var/hyperledger/orderer/genesis.block").name(OrdererName+"-claim0");voList.add(vm2);
        V1VolumeMount vm3 = new V1VolumeMount().mountPath("/var/hyperledger/orderer/msp").name(OrdererName+"-claim1");voList.add(vm3);
        V1VolumeMount vm4 = new V1VolumeMount().mountPath("/var/hyperledger/orderer/tls").name(OrdererName+"-claim2");voList.add(vm4);
        V1Container OrdererServiceContainer = new V1Container().args(argsList).args(argsList)
                .env(EnvList)
                .image("hyperledger/fabric-orderer:amd64-2.1.0")
                .name("orderer")
                .ports(portList)
                .volumeMounts(voList)
                .workingDir("/opt/gopath/src/github.com/hyperledger/fabric");
        List<V1Container> cList = new ArrayList<>();cList.add(OrdererServiceContainer);
        return cList;
    }
    /**
     * 生成peer deployment spec中的template模块
     * */
    public V1PodTemplateSpec generatePeerTemplate(Peer peer,String orgName,String netName){
        Map<String,String> map = new HashMap<>();
        map.put("app",peer.getPeerName());
        V1ObjectMeta om = new V1ObjectMeta().labels(map);
        V1HostPathVolumeSource hps1 = new V1HostPathVolumeSource().path("/var/run").type("Directory");
        V1HostPathVolumeSource hps2 = new V1HostPathVolumeSource().path("/mnt/nfsdata/fabric/"+netName+"/crypto-config/peerOrganizations/"+orgName+"/peers/"+peer.getPeerName()+"/msp").type("Directory");
        V1HostPathVolumeSource hps3 = new V1HostPathVolumeSource().path("/mnt/nfsdata/fabric/"+netName+"/crypto-config/peerOrganizations/"+orgName+"/peers/"+peer.getPeerName()+"/tls").type("Directory");
        V1HostPathVolumeSource hps4 = new V1HostPathVolumeSource().path("/mnt/nfsdata/fabric/"+netName+"/pau/storage/"+peer.getPeerName()).path("DirectoryOrCreate");
        V1HostPathVolumeSource hps5 = new V1HostPathVolumeSource().path("/mnt/nfsdata/fabric/"+netName+"/buildpack");
        V1KeyToPath ktp = new V1KeyToPath().key("core.yaml").path("core.yaml");
        List<V1KeyToPath> ktpList = new ArrayList<>();ktpList.add(ktp);
        V1ConfigMapVolumeSource cmv = new V1ConfigMapVolumeSource().name("builders-config").items(ktpList);
        List<V1Volume> vList = new ArrayList<>();
        V1Volume v1 = new V1Volume().name(peer.getPeerName()+"claim0").hostPath(hps1);vList.add(v1);
        V1Volume v2 = new V1Volume().name(peer.getPeerName()+"claim1").hostPath(hps2);vList.add(v2);
        V1Volume v3 = new V1Volume().name(peer.getPeerName()+"claim2").hostPath(hps3);vList.add(v3);
        V1Volume v4 = new V1Volume().name(peer.getPeerName().split("-")[0]+"-persistentdata").hostPath(hps4);vList.add(v4);
        V1Volume v5 = new V1Volume().name("external-builder").hostPath(hps5);vList.add(v5);
        V1Volume v6 = new V1Volume().name("builders-config").configMap(cmv);vList.add(v6);
        V1PodSpec ps = new V1PodSpec().containers(generatePeerSecondSpecContainer(orgName,peer))
                .restartPolicy("Always")
                .volumes(vList);
        V1PodTemplateSpec pts = new V1PodTemplateSpec().metadata(om)
                .spec(ps);
        return pts;
    }

    /**
     * 生成orderer deployment spec中的template模块
     * */
    public V1PodTemplateSpec generateOrdererTemplate(String OrdererName,String netName){
        Map<String,String> map = new HashMap<>();
        map.put("app",OrdererName);
        V1ObjectMeta om = new V1ObjectMeta().labels(map);
        V1HostPathVolumeSource hps1 = new V1HostPathVolumeSource().path("/mnt/nfsdata/pau/storage/orderer").type("DirectoryOrCreate");
        V1HostPathVolumeSource hps2 = new V1HostPathVolumeSource().path("/mnt/nfsdata/fabric/"+netName+"/channel-artifacts/genesis.block");
        V1HostPathVolumeSource hps3 = new V1HostPathVolumeSource().path("/mnt/nfsdata/fabric/"+netName+"/crypto-config/ordererOrganizations/consortium/orderers/"+OrdererName+"/msp");
        V1HostPathVolumeSource hps4 = new V1HostPathVolumeSource().path("/mnt/nfsdata/fabric/"+netName+"/crypto-config/ordererOrganizations/consortium/orderers/"+OrdererName+"/tls");
        List<V1Volume> vList = new ArrayList<>();
        V1Volume v1 = new V1Volume().name(OrdererName+"-persistentdata").hostPath(hps1);vList.add(v1);
        V1Volume v2 = new V1Volume().name(OrdererName+"-claim0").hostPath(hps2);vList.add(v2);
        V1Volume v3 = new V1Volume().name(OrdererName+"-claim1").hostPath(hps3);vList.add(v3);
        V1Volume v4 = new V1Volume().name(OrdererName+"-claim2").hostPath(hps4);vList.add(v4);
        V1PodSpec ps = new V1PodSpec().containers(generateOrdererSecondSpecContainer(OrdererName))
                .restartPolicy("Always")
                .volumes(vList);
        V1PodTemplateSpec pts = new V1PodTemplateSpec().metadata(om)
                .spec(ps);
        return pts;
    }

    /**
     * 生成cli deployment spec中的template模块
     * */
    public V1PodTemplateSpec generateCliTemplate(String orgName,String netName){
        Map<String,String> map = new HashMap<>();
        map.put("app","cli-"+orgName);
        V1ObjectMeta om = new V1ObjectMeta().labels(map);
        List<V1Volume> vList = new ArrayList<>();
        V1HostPathVolumeSource hpvs1 = new V1HostPathVolumeSource().path("/var/run/fabric/").type("Directory");
        V1HostPathVolumeSource hpvs2= new V1HostPathVolumeSource().path("/mnt/nfsdata/fabric/"+netName+"/chaincode").type("Directory");
        V1HostPathVolumeSource hpvs3 = new V1HostPathVolumeSource().path("/mnt/nfsdata/fabric/"+netName+"/channel-artifacts").type("Directory");
        V1HostPathVolumeSource hpvs4 = new V1HostPathVolumeSource().path("/mnt/nfsdata/fabric/"+netName+"/crypto-config").type("Directory");
        V1Volume v1 = new V1Volume().name("cli-claim0").hostPath(hpvs1);vList.add(v1);
        V1Volume v2 = new V1Volume().name("cli-claim1").hostPath(hpvs2);vList.add(v2);
        V1Volume v3 = new V1Volume().name("cli-claim2").hostPath(hpvs3);vList.add(v3);
        V1Volume v4 = new V1Volume().name("cli-claim3").hostPath(hpvs4);vList.add(v4);
        V1PodSpec ps = new V1PodSpec().containers(generateCliSecondSpecContainer(orgName))
                .restartPolicy("Always")
                .volumes(vList);
        V1PodTemplateSpec pts = new V1PodTemplateSpec().metadata(om)
                .spec(ps);
        return pts;
    }
    /**
     * 生成ca deployment spec中的template模块
     * */
    public V1PodTemplateSpec generateCaTemplate(String orgName,String netName){
        Map<String,String> map = new HashMap<>();
        map.put("app","ca-"+orgName);
        V1ObjectMeta om = new V1ObjectMeta().labels(map);
        V1HostPathVolumeSource hps1 = new V1HostPathVolumeSource().path("/mnt/nfsdata/fabric/"+netName+"/crypto-config/peerOrganizations/"+orgName+"/ca/").type("Directory");
        List<V1Volume> vList = new ArrayList<>();
        V1Volume v1 = new V1Volume().name("ca-"+orgName+"-claim0").hostPath(hps1);vList.add(v1);
        V1PodSpec ps = new V1PodSpec().containers(generateCaSecondSpecContainer(orgName))
                .restartPolicy("Always")
                .volumes(vList);
        V1PodTemplateSpec pts = new V1PodTemplateSpec().metadata(om)
                .spec(ps);
        return pts;
    }
    /**
     * 生成chaincode deployment spec中的template模块
     * */
    public V1PodTemplateSpec generateChaincodeTemplate(String orgName,String NameSpace){
        Map<String,String> map = new HashMap<>();
        map.put("app","chaincode-marbles-"+orgName);
        V1ObjectMeta om = new V1ObjectMeta().labels(map);;
        V1PodSpec ps = new V1PodSpec().containers(generateChaincodeSecondSpecContainer(orgName));
        V1PodTemplateSpec pts = new V1PodTemplateSpec().metadata(om)
                .spec(ps);
        return pts;
    }
}
