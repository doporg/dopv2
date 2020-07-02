package com.clsaa.dop.server.baas.Service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Service(value = "K8sYamlGenerateService")
public class K8sYamlGenerateService {

    public void generateConfigMap(String NameSpace) throws IOException {
        String content = this.getFromYaml("src/main/resources/builders-config-t.yaml");
        content = content.replace("<NameSpace>",NameSpace);
        File newFile = new File("src/main/resources/builders-config.yaml");
        FileWriter fw = new FileWriter(newFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }
    public void generatePeerDeploymentYaml(String PeerName,String NameSpace,String orgName) throws IOException {
        String content = this.getFromYaml("src/main/resources/peer-deployment.yaml");
        content = content.replace("<peerName>",PeerName);
        content = content.replace("<peerFirstName>",PeerName.split("-")[0]);
        content = content.replace("<NameSpace>",NameSpace);
        content = content.replace("<orgName>",orgName);
        File newFile = new File("src/main/resources/"+PeerName+"-deployment.yaml");
        FileWriter fw = new FileWriter(newFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }
    public void generatePeerServiceYaml(String PeerName,String NameSpace) throws IOException {
        String content = this.getFromYaml("src/main/resources/peer-svc.yaml");
        content = content.replace("<peerName>",PeerName);
        content = content.replace("<NameSpace>",NameSpace);
        File newFile = new File("src/main/resources/"+PeerName+"-svc.yaml");
        FileWriter fw = new FileWriter(newFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }
    public void generateChaincodeServiceYaml(String chaincodeName,String NameSpace,String orgName) throws IOException {
        String content = this.getFromYaml("src/main/resources/peer-svc.yaml");
        content = content.replace("<orgName>",orgName);
        content = content.replace("<NameSpace>",NameSpace);
        content = content.replace("<chaincodeName>",chaincodeName);

        File newFile = new File("src/main/resources/chaincode-"+orgName+"-"+chaincodeName+"-service.yaml");
        FileWriter fw = new FileWriter(newFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }
    public void replaceCCidInShell1(String ccid,String NameSpace) throws IOException {
        String content = this.getFromYaml("src/main/resources/org1Commit.sh");
        content = content.replace("<ccid>",ccid);
        File newFile = new File("src/main/resources/"+NameSpace+"-chaincode"+"-org1Commit.sh");
        FileWriter fw = new FileWriter(newFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }
    public void replaceCCidInShell2(String ccid,String NameSpace) throws IOException {
        String content = this.getFromYaml("src/main/resources/org2Commit.sh");
        content = content.replace("<ccid>",ccid);
        File newFile = new File("src/main/resources/"+NameSpace+"-chaincode"+"-org2Commit.sh");
        FileWriter fw = new FileWriter(newFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }
    public void replaceChaincodeShell(String method,String args) throws IOException {
        String content = this.getFromYaml("src/main/resources/chaincodeOp.sh");
        content = content.replace("<method>",method);
        content = content.replace("<args>",args);
        File newFile = new File("src/main/resources/"+method+"-chaincodeOp.sh");
        FileWriter fw = new FileWriter(newFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }
    public void generateChaincodeDeploymentYaml(String chaincodeName,String NameSpace,String orgName,String git,String ccid) throws IOException {
        String content = this.getFromYaml("src/main/resources/cli-deployment.yaml");
        content = content.replace("<orgName>",orgName);
        content = content.replace("<NameSpace>",NameSpace);
        content = content.replace("<git>",git);
        content = content.replace("<CCID>",ccid);
        content = content.replace("<chaincodeName>",chaincodeName);
        File newFile = new File("src/main/resources/chaincode/"+orgName+"-"+chaincodeName+"-deployment.yaml");
        FileWriter fw = new FileWriter(newFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }

    public void generateCliDeploymentYaml(String orgName,String NameSpace) throws IOException {
        String content = this.getFromYaml("src/main/resources/cli-deployment.yaml");
        content = content.replace("<orgName>",orgName);
        content = content.replace("<NameSpace>",NameSpace);
        File newFile = new File("src/main/resources/"+orgName+"-cli-deployment.yaml");
        FileWriter fw = new FileWriter(newFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }

    public void generateCaDeploymentYaml(String orgName,String NameSpace) throws IOException {
        String content = this.getFromYaml("src/main/resources/ca-deployment.yaml");
        content = content.replace("<orgName>",orgName);
        content = content.replace("<NameSpace>",NameSpace);
        File newFile = new File("src/main/resources/"+orgName+"-ca-deployment.yaml");
        FileWriter fw = new FileWriter(newFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }
    public void replaceChannelShell(String channelName,int count,String NameSpace) throws IOException {
        String content;
        if(count==1){
            content = this.getFromYaml("src/main/resources/firstChannel.sh");
            content = content.replace("<channelName>",channelName);
            File newFile = new File("src/main/resources/"+NameSpace+"-firstChannel.sh");
            FileWriter fw = new FileWriter(newFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        }
        else{
            content = this.getFromYaml("src/main/resources/secChannel.sh");
            content = content.replace("<channelName>",channelName);
            File newFile = new File("src/main/resources/"+NameSpace+"-secChannel.sh");
            FileWriter fw = new FileWriter(newFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        }
    }
    public void generateCaServiceYaml(String orgName,String NameSpace) throws IOException {
        String content = this.getFromYaml("src/main/resources/ca-svc.yaml");
        content = content.replace("<orgName>",orgName);
        content = content.replace("<NameSpace>",NameSpace);
        File newFile = new File("src/main/resources/"+orgName+"-ca-svc.yaml");
        FileWriter fw = new FileWriter(newFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }
    public void generateOrdererDeploymentYaml(String ordererName,List<String> otherOrdererNameList, String NameSpace) throws IOException {
        String content = this.getFromYaml("src/main/resources/orderer-deployment.yaml");
        content = content.replace("<ordererName>",ordererName);
        content = content.replace("<NameSpace>",NameSpace);
        for(int i=0;i<otherOrdererNameList.size();i++){
            if(i<otherOrdererNameList.size()-1)
                content = content.replace("<otherOrdererName>",otherOrdererNameList.get(i)+"\n                        - <otherOrdererName>");
            else
                content = content.replace("<otherOrdererName>",otherOrdererNameList.get(i));
        }
        File newFile = new File("src/main/resources/"+ordererName+"-deployment.yaml");
        FileWriter fw = new FileWriter(newFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }
    public void generateOrdererServiceYaml(String ordererName,String NameSpace) throws IOException {
        String content = this.getFromYaml("src/main/resources/orderer-svc.yaml");
        content = content.replace("<ordererName>",ordererName);
        content = content.replace("<NameSpace>",NameSpace);
        File newFile = new File("src/main/resources/"+ordererName+"-svc.yaml");
        FileWriter fw = new FileWriter(newFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }

    public String getFromYaml(String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        File ttfFile = new File("src/main/resources/tem.yaml");
        FileUtils.copyInputStreamToFile(inputStream, ttfFile);
        FileReader reader = new FileReader(ttfFile);
        BufferedReader br = new BufferedReader(reader);
        String content = "";
        StringBuilder sb = new StringBuilder();
        while (content != null) {
            content = br.readLine();

            if (content == null) {
                break;
            }

            sb.append(content + '\n');
        }
        br.close();
        String s =sb.toString();
        ttfFile.delete();
        return s;
    }


}

