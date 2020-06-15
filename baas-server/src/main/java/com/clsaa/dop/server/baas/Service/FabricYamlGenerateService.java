package com.clsaa.dop.server.baas.Service;

import com.clsaa.dop.server.baas.model.yamlMo.Orderer;
import com.clsaa.dop.server.baas.model.yamlMo.Organization;
import com.clsaa.dop.server.baas.model.yamlMo.Peer;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Service(value = "FabricYamlGenerateService")
public class FabricYamlGenerateService {

    /**
     * 将resources里的configtx-template.ymal根据传进来的参数生成对应的yaml文件
     * */
    public void replaceWithConfigtxTemplate(String Namespace,List<Organization> orgList, List<Orderer> ordererList, String consensus)throws IOException {
        String content = this.getFromYaml("src/main/resources/configtx-template.yaml");
        //下面一行的content中还有一个<AnchorPeerPort>没有替换成相应的端口
        content = content.replace("<orgMspInfo>",generateOrgInfo(orgList));
        content = content.replace("<AnchorPeerPort>","7051");
        content = content.replace("<OrdererType>",consensus);
        //下面替换完&OrdererDefaults
        for(int i=0;i<ordererList.size();i++){
            if(i<ordererList.size()-1){
                content = content.replace("<orderList>","- "+ordererList.get(i).getOrderName()+":"+ordererList.get(i).getOrderport()+"\n        <orderList>");
            }
            else {
                content = content.replace("<orderList>","- "+ordererList.get(i).getOrderName()+":"+ordererList.get(i).getOrderport());
            }
        }
        //下面是共识的类型，如果是EtcdRaft就要多加内容
        if(consensus.equals("etcdraft")){
            content = content.replace("<consensus>",generateEtcdraft(ordererList));
        }
        else{
            content = content.replace("<consensus>"," ");
        }
        //下面替换Profiles
        for(int i=0;i<orgList.size();i++){
            if(i<ordererList.size()-1){
                content = content.replace("<ProfilesOrg>","- *"+orgList.get(i).getOrgName()+"\n                    <ProfilesOrg>");
                content = content.replace("<ProfilesOrg2>","- *"+orgList.get(i).getOrgName()+"\n                <ProfilesOrg>");
            }
            else{
                content = content.replace("<ProfilesOrg>","- *"+orgList.get(i).getOrgName());
                content = content.replace("<ProfilesOrg2>","- *"+orgList.get(i).getOrgName());
            }
        }
        File newfile = new File("src/main/resources/configtx-"+Namespace+".yaml");
        FileWriter fw = new FileWriter(newfile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }

    /**
     *生成configtx.yaml中ETCDRAFT相关内容
     * */
    public String generateEtcdraft(List<Orderer> ordererList){
        StringBuilder returnContent = new StringBuilder("");
        returnContent.append("    EtcdRaft:\n        Consenters:\n");
        for(int i=0;i<ordererList.size();i++){
            if(i<ordererList.size()-1){
                String pattern = "            - Host: <ordererName>\n              Port: <ordererPort>\n              ClientTLSCert: crypto-config/ordererOrganizations/consortium/orderers/<ordererName>/tls/server.crt\n              ServerTLSCert: crypto-config/ordererOrganizations/consortium/<ordererName>/orderer0/tls/server.crt\n";
                pattern = pattern.replace("<ordererName>",ordererList.get(i).getOrderName());
                pattern = pattern.replace("<ordererPort>",ordererList.get(i).getOrderport());
                returnContent.append(pattern);
            }
            else{
                String pattern = "            - Host: <ordererName>\n              Port: <ordererPort>\n              ClientTLSCert: crypto-config/ordererOrganizations/consortium/orderers/<ordererName>/tls/server.crt\n              ServerTLSCert: crypto-config/ordererOrganizations/consortium/<ordererName>/orderer0/tls/server.crt\n";
                pattern = pattern.replace("<ordererName>",ordererList.get(i).getOrderName());
                pattern = pattern.replace("<ordererPort>",ordererList.get(i).getOrderport());
                returnContent.append(pattern);
            }
        }
        returnContent.append("        Options:\n            TickInterval: 500ms\n            ElectionTick: 10\n            HeartbeatTick: 1\n            MaxInflightBlocks: 5\n            SnapshotIntervalSize: 16 MB\n");
        returnContent.append("    Organizations:\n    Policies:\n        Readers:\n            Type: ImplicitMeta\n            Rule: \"ANY Readers\"\n");
        returnContent.append("        Writers:\n            Type: ImplicitMeta\n            Rule: \"ANY Writers\"\n        Admins:\n            Type: ImplicitMeta\n            Rule: \"MAJORITY Admins\"\n");
        returnContent.append("        BlockValidation:\n            Type: ImplicitMeta\n            Rule: \"ANY Writers\"");
        return returnContent.toString().trim();
    }

    /**
     * 将resources里的configtx-template.ymal根据传进来的参数生成对应的yaml文件
     * */
    public String generateOrgInfo(List<Organization> orgList){
        StringBuilder content = new StringBuilder("");
        StringBuilder returnContent = new StringBuilder("");
        content.append("- &<orgName>\n        Name: <orgName>MSP\n        ID: <orgName>MSP\n        ");
        content.append("MSPDir: crypto-config/peerOrganizations/<orgName>/msp \n        Policies: &<orgName>Policies\n            ");
        content.append("Readers:\n                 Type: Signature\n                Rule: \"OR('<orgName>MSP.admin', '<orgName>MSP.peer', '<orgName>MSP.client')\"\n            ");
        content.append("Writers:\n                 Type: Signature\n                Rule: \"OR('<orgName>MSP.admin', '<orgName>MSP.peer', '<orgName>MSP.client')\"\n            ");
        content.append("Admins:\n                 Type: Signature\n                Rule: \"OR('<orgName>MSP.admin')\"\n            ");
        content.append("Endorsement:\n                 Type: Signature\n                Rule: \"OR('<orgName>MSP.admin')\"\n        ");
        content.append("AnchorPeers:\n            - Host: peer0-<orgName>\n              Port: <AnchorPeerPort>");
        for(int i=0;i<orgList.size();i++){
            String con = content.toString();
            con = con.replace("<orgName>",orgList.get(i).getOrgName());
//            System.out.println(con);
            returnContent.append(con);
            if(i!=orgList.size()-1){
                returnContent.append("\n    ");
            }
        }
//        System.out.println( returnContent.toString().trim());
        return returnContent.toString().trim();
    }

    /**
     * 将resources里的crypto-config-template.ymal根据传进来的参数生成对应的yaml文件
     * */
    public void replaceWithCryptoconfigTemplate(String Namespace,List<Orderer> ordererList, List<Organization> orgList) throws IOException{
        String content = this.getFromYaml("src/main/resources/crypto-config-template.yaml");
        //写OrdererOrgs的Specs:
        for(int i=0;i<ordererList.size();i++){
            if(i<ordererList.size()-1){
                content = content.replace("<OrdererHostName>","- Hostname: "+ordererList.get(i).getOrderName()+"\n        CommonName: "+ordererList.get(i).getOrderName()+"\n      <OrdererHostName>");
            }
            else{
                content = content.replace("<OrdererHostName>","- Hostname: "+ordererList.get(i).getOrderName()+"\n        CommonName: "+ordererList.get(i).getOrderName());
            }
        }
        String PeerOrgs = "- Name: <OrgName>\n    Domain: <OrgDomain>\n    EnableNodeOUs: true\n    Specs:\n      <PeerSpecs>\n    Users:\n      Count: 1";
        for(int i=0;i<orgList.size();i++){
            if(i<orgList.size()-1){
                content=content.replace("<OrgInfo>",replacePeerOrgs(PeerOrgs,orgList.get(i).getOrgName(),orgList.get(i).getOrgName(),orgList.get(i).getPeers())+"\n  <OrgInfo>");
            }
            else {
                content=content.replace("<OrgInfo>",replacePeerOrgs(PeerOrgs,orgList.get(i).getOrgName(),orgList.get(i).getOrgName(),orgList.get(i).getPeers()));
            }
        }
        File newfile = new File("src/main/resources/crypto-config-"+Namespace+".yaml");
        FileWriter fw = new FileWriter(newfile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }

    /**
     * 替换crypto-config-template.ymal中的peerSpec
     * */
    public String replacePeerSpec(List<Peer> peers){
        String pattern="- Hostname: <peerHostName>\n        CommonName: <peerCommonName>";
        StringBuilder specs= new StringBuilder("");
        int size = peers.size();
        for(int i=0;i<size;i++){
            if(i<size-1) {
                pattern = pattern.replace("<peerHostName>", "peer" + String.valueOf(i));
                pattern = pattern.replace("<peerCommonName>", peers.get(i).getPeerName()+"\n      ");
                specs.append(pattern);
            }
            else{
                pattern = pattern.replace("<peerHostName>", "peer" + String.valueOf(i));
                pattern = pattern.replace("<peerCommonName>", peers.get(i).getPeerName());
                specs.append(pattern);
            }
        }
        return specs.toString().trim();
    }
    /**
     * 替换crypto-config-template.ymal中的OrgName,Domain
     * */
    public String replacePeerOrgs(String PeerOrgs,String OrgName,String OrgDomain,List<Peer> peers){
        PeerOrgs = PeerOrgs.replace("<OrgName>",OrgName);
        PeerOrgs = PeerOrgs.replace("<OrgDomain>",OrgDomain);
        PeerOrgs = PeerOrgs.replace("<PeerSpecs>",replacePeerSpec(peers));
        return PeerOrgs;
    }

    /**
     * 根据文件路径获得对应yaml模板的文件，返回内容的String
     * */
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

