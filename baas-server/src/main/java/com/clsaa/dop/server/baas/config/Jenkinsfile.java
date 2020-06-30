package com.clsaa.dop.server.baas.config;

import com.clsaa.dop.server.baas.model.yamlMo.Organization;

import java.util.List;

/**
 * 生成jenkinsfile
 *
 * @author Monhey
 */
public class Jenkinsfile {

    private String NameSpace;
    private String Script;

    public Jenkinsfile() {
    }
    public Jenkinsfile(String NameSpace){
        this.NameSpace = NameSpace;
    }

    public void setCreateFabricScript(String NameSpace){
        StringBuilder sb = new StringBuilder("");
        sb.append("cd /mnt/nfsdata/fabric\n");
        sb.append("cp fabricOps.sh /mnt/nfsdata/fabric/"+NameSpace+"\n");
        sb.append("cd "+NameSpace+"\n");
        sb.append("chmod 777 fabricOps.sh\n");
        sb.append("sh fabricOps.sh start\n");
        sb.append("cd ../\n");
        sb.append("cp -r chaincode "+NameSpace+"/\n");
        sb.append("cp -r buildpack "+NameSpace+"/");
        this.Script=sb.toString();
    }

    public void tarAndPushInPod(String podName1,String podName2,String NameSpace){
        StringBuilder sb = new StringBuilder("");
        sb.append("cd /mnt/nfsdata/fabric/\n");
        sb.append("kubectl cp marbles-org1.tgz -n "+NameSpace+" "+podName1+":/opt/gopath/src/github.com/hyperledger/fabric/peer/marbles-org1.tgz\n");
        sb.append("kubectl cp marbles-org2.tgz -n "+NameSpace+" "+podName2+":/opt/gopath/src/github.com/hyperledger/fabric/peer/marbles-org2.tgz\n");
        sb.append("kubectl exec "+podName1+" -n "+NameSpace+" -- peer lifecycle chaincode install marbles-org1.tgz\n");
        sb.append("kubectl exec "+podName2+" -n "+NameSpace+" -- peer lifecycle chaincode install marbles-org2.tgz");
        this.Script=sb.toString();
    }
    public void chaincodeOps(String cliPodName1,String cliPodName2,String peerPodName,String NameSpace){
        StringBuilder sb = new StringBuilder("");
        sb.append("cd /mnt/nfsdata/fabric/"+NameSpace+"/chaincode\n");
        sb.append("kubectl create -f k8s/\n");
        sb.append("kubectl cp org1Commit.sh -n "+NameSpace+" "+cliPodName1+":/opt/gopath/src/github.com/hyperledger/fabric/peer/org1Commit.sh\n");
        sb.append("kubectl cp org2Commit.sh -n "+NameSpace+" "+cliPodName2+":/opt/gopath/src/github.com/hyperledger/fabric/peer/org2Commit.sh\n");
        sb.append("kubectl cp peerCommit.sh -n "+NameSpace+" "+peerPodName+":/opt/gopath/src/github.com/hyperledger/fabric/peer/peerCommit.sh\\n");
        sb.append("kubectl exec "+cliPodName1+" -n "+NameSpace+" -- sh org1Commit.sh\n");
        sb.append("kubectl exec "+cliPodName2+" -n "+NameSpace+" -- sh org2Commit.sh\n");
        sb.append("kubectl exec "+peerPodName+" -n "+NameSpace+" -- sh peerCommit.sh\n");
        this.Script=sb.toString();
    }
    public void chaincodeInvoke(String podName,String NameSpace){
        StringBuilder sb = new StringBuilder("");
        sb.append("cd /mnt/nfsdata/fabric/"+NameSpace+"/chaincode\n");
        sb.append("kubectl cp chaincodeOp.sh -n "+NameSpace+" "+podName+":/opt/gopath/src/github.com/hyperledger/fabric/peer/chaincodeOp.sh\n");
        this.Script=sb.toString();
    }
    public void CpAndExecFirstChannel(String podName,String NameSpace){
        StringBuilder sb = new StringBuilder("");
        //kubectl cp newmarbles-org1.tgz -n hyperledger cli-org1-6dc897958b-ldhmq:/opt/gopath/src/github.com/hyperledger/fabric/peer/newmarbles-org1.tgz
        sb.append("cd /mnt/nfsdata/fabric/"+NameSpace+"\n");
        sb.append("chmod 777 *\n");
        sb.append("kubectl cp firstChannel.sh -n "+NameSpace+" "+podName+":/opt/gopath/src/github.com/hyperledger/fabric/peer/firstChannel.sh\n");
        sb.append("kubectl exec "+podName+" -n "+NameSpace+"-- sh firstChannel.sh");
        this.Script=sb.toString();
    }
    public void CpAndExecSecChannel(String podName,String NameSpace){
        StringBuilder sb = new StringBuilder("");
        //kubectl cp newmarbles-org1.tgz -n hyperledger cli-org1-6dc897958b-ldhmq:/opt/gopath/src/github.com/hyperledger/fabric/peer/newmarbles-org1.tgz
        sb.append("cd /mnt/nfsdata/fabric/"+NameSpace+"\n");
        sb.append("kubectl cp firstChannel.sh -n "+NameSpace+" "+podName+":/opt/gopath/src/github.com/hyperledger/fabric/peer/secChannel.sh\n");
        sb.append("kubectl exec "+podName+" -n "+NameSpace+"-- sh secChannel.sh");
        this.Script=sb.toString();
    }
    public void setKubectlApply(List<Organization> orgList,String NameSpace){
        StringBuilder sb = new StringBuilder("");
        sb.append("cd /mnt/nfsdata/fabric/"+NameSpace+"\n");
        sb.append("kubectl create ns "+NameSpace+"\n");
        sb.append("kubectl create -f orderer-service/\n");
        for(Organization org:orgList){
            sb.append("kubectl create -f"+org.getOrgName()+"/\n");
        }
        this.Script=sb.toString();
    }
    public void setCpAndExplainBlockData(String Namespace,String podName,int height,String ChannelName){
        StringBuilder sb = new StringBuilder();
        sb.append("cd /usr/share/nginx/html\n");
        sb.append("mkdir "+Namespace+"\n");
        for(int i=height-1;i>=0;i--) {
            sb.append("kubectl cp -n " + Namespace + " " + podName + ":/opt/gopath/src/github.com/hyperledger/fabric/peer/"+ChannelName+"_" + i + ".block /usr/share/nginx/html/" + Namespace + "/"+ChannelName+"_" + i + ".block\n");
        }
        sb.append("cd /usr/share/nginx/html/"+Namespace+"\n");
        for(int i=height-1;i>=0;i--){
            sb.append("/root/go/bin/configtxgen -inspectBlock "+ChannelName+"_"+i+".block > "+ChannelName+"_"+i+".json\n");
        }
        this.Script = sb.toString();
    }

    public void setChaincodeInstall(String Namespace,String podName1,String podName2){
        StringBuilder sb = new StringBuilder();
        sb.append("cd /mnt/nfsdata/fabric/\n");
        sb.append("cp marbles-org1.tgz /mnt/ntsdata/fabric/"+Namespace+"\n");
        sb.append("cp marbles-org2.tgz /mnt/ntsdata/fabric/"+Namespace+"\n");
        sb.append("kubectl exec "+podName1+" -n "+Namespace+" -- peer lifecycle chaincode install marbles-org1.tgz");
        sb.append("kubectl exec "+podName2+" -n "+Namespace+" -- peer lifecycle chaincode install marbles-org2.tgz");
        this.Script=sb.toString();
    }
    public void setInstallChaincodeInChannle(String Namespace,String podName1,String podName2,String peerPodName,String ChannelName,String git){
        StringBuilder sb = new StringBuilder();
        sb.append("cd /mnt/nfsdata/fabric/"+Namespace+"\n");
        sb.append("kubectl exec "+podName1+" -n "+Namespace+" peer lifecycle chaincode approveformyorg --channelID "+ChannelName+" --name marbles --version 1.0 --init-required --package-id marbles:"+git+" --sequence 1 -o orderer0:7050 --tls --cafile $ORDERER_CA --signature-policy \"AND ('org1MSP.peer','org2MSP.peer')\"\n");
        sb.append("kubectl exec "+podName2+" -n "+Namespace+" peer lifecycle chaincode approveformyorg --channelID "+ChannelName+" --name marbles --version 1.0 --init-required --package-id marbles:"+git+" --sequence 1 -o orderer0:7050 --tls --cafile $ORDERER_CA --signature-policy \"AND ('org1MSP.peer','org2MSP.peer')\"\n");
        sb.append("kubectl exec "+peerPodName+" -n "+Namespace+" -- peer lifecycle chaincode commit -o orderer0:7050 --channelID "+ChannelName+" --name marbles --version 1.0 --sequence 1 --init-required --tls true --cafile $ORDERER_CA --peerAddresses peer0-org1:7051 --tlsRootCertFiles /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1/peers/peer0-org1/tls/ca.crt --peerAddresses peer0-org2:7051 --tlsRootCertFiles /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org2/peers/peer0-org2/tls/ca.crt --signature-policy \"AND ('org1MSP.peer','org2MSP.peer')\"");
        this.Script=sb.toString();
    }
    public String getScript(){
        return this.Script;
    }

}