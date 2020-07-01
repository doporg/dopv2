package com.clsaa.dop.server.baas.Service;

import com.clsaa.dop.server.baas.config.JenkinsClient;
import com.clsaa.dop.server.baas.config.Jenkinsfile;
import com.clsaa.dop.server.baas.config.JobConfig;
import com.clsaa.dop.server.baas.model.yamlMo.Organization;
import com.offbytwo.jenkins.JenkinsServer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Service
public class JenkinsService {
    private String jenkinsURI;
    private String user;
    private String pwd;
    private JenkinsServer jenkins;

    private JenkinsClient jenkinsClient = new JenkinsClient();


    public JenkinsService() {
        this.jenkinsURI = jenkinsClient.getUri();
        this.user = jenkinsClient.getUsername();
        this.pwd = jenkinsClient.getPassword();
        try {
            this.jenkins = new JenkinsServer(new URI(jenkinsURI), user, pwd);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void getJobList(){
        try {
            // 获取 Job 列表
            Map<String,Job> jobs = jenkins.getJobs();
            for (Job job:jobs.values()){
                System.out.println(job.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * 创建流水线
     * param: 流水线的信息, 版本
     * */
    public String createCreatFabricJob(String namespace) {
        Jenkinsfile jenkinsfile = new Jenkinsfile();
        jenkinsfile.setCreateFabricScript(namespace);
        JobConfig jobConfig = new JobConfig(jenkinsfile.getScript());
        String name = "Create-"+namespace+"-Fabric";
        try {
            if (jenkins.getJob(name) == null) {
                jenkins.createJob(name,jobConfig.getXml());
            } else {
                jenkins.deleteJob(name);
                jenkins.createJob(name,jobConfig.getXml());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return e.toString();
        }
        return name;
    }
    public String CpInPodTarJob(String podName1,String podName2,String NameSpace,String chaincodeName) {
        Jenkinsfile jenkinsfile = new Jenkinsfile();
        jenkinsfile.tarAndPushInPod(podName1,podName2,NameSpace);
        JobConfig jobConfig = new JobConfig(jenkinsfile.getScript());
        String name = "Createchaincode-"+chaincodeName+"-"+NameSpace;
        try {
            if (jenkins.getJob(name) == null) {
                jenkins.createJob(name,jobConfig.getXml());
            } else {
                jenkins.deleteJob(name);
                jenkins.createJob(name,jobConfig.getXml());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return e.toString();
        }
        return name;
    }
    public String ChaincodeOp(String cliPodName1,String cliPodName2,String peerPodName,String NameSpace) {
        Jenkinsfile jenkinsfile = new Jenkinsfile();
        jenkinsfile.chaincodeOps(cliPodName1,cliPodName2,peerPodName,NameSpace);
        JobConfig jobConfig = new JobConfig(jenkinsfile.getScript());
        String name = "CreatechaincodeShell-"+NameSpace;
        try {
            if (jenkins.getJob(name) == null) {
                jenkins.createJob(name,jobConfig.getXml());
            } else {
                jenkins.deleteJob(name);
                jenkins.createJob(name,jobConfig.getXml());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return e.toString();
        }
        return name;
    }
    public String ChaincodeInvoke(String podName,String NameSpace,String method){
        Jenkinsfile jenkinsfile = new Jenkinsfile();
        jenkinsfile.chaincodeInvoke(podName,NameSpace);
        JobConfig jobConfig = new JobConfig(jenkinsfile.getScript());
        String name = "invoke-"+method+"-"+NameSpace;
        try {
            if (jenkins.getJob(name) == null) {
                jenkins.createJob(name,jobConfig.getXml());
            } else {
                jenkins.deleteJob(name);
                jenkins.createJob(name,jobConfig.getXml());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return e.toString();
        }
        return name;
    }
    public String createCreatK8sFabricJob(String namespace,List<Organization> orgList) {
        Jenkinsfile jenkinsfile = new Jenkinsfile();
        jenkinsfile.setKubectlApply(orgList,namespace);
        JobConfig jobConfig = new JobConfig(jenkinsfile.getScript());
        String name = "Create-"+namespace+"-FabricNet";
        try {
            if (jenkins.getJob(name) == null) {
                jenkins.createJob(name,jobConfig.getXml());
            } else {
                jenkins.deleteJob(name);
                jenkins.createJob(name,jobConfig.getXml());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return e.toString();
        }
        return name;
    }

    public String createFirstChannel(String NameSpace,String podName,String ChannelName){
        Jenkinsfile jenkinsfile = new Jenkinsfile();
        jenkinsfile.CpAndExecFirstChannel(podName,NameSpace);
        JobConfig jobConfig = new JobConfig(jenkinsfile.getScript());
        String name = "Crater-"+ NameSpace+"-"+ChannelName;
        try {
            if (jenkins.getJob(name) == null) {
                jenkins.createJob(name,jobConfig.getXml());
            } else {
                jenkins.deleteJob(name);
                jenkins.createJob(name,jobConfig.getXml());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return e.toString();
        }
        return name;
    }
    public String createSecChannel(String NameSpace,String podName,String ChannelName){
        Jenkinsfile jenkinsfile = new Jenkinsfile();
        jenkinsfile.CpAndExecSecChannel(podName,NameSpace);
        JobConfig jobConfig = new JobConfig(jenkinsfile.getScript());
        String name = "Crater-"+ NameSpace+"-"+ChannelName+"2";
        try {
            if (jenkins.getJob(name) == null) {
                jenkins.createJob(name,jobConfig.getXml());
            } else {
                jenkins.deleteJob(name);
                jenkins.createJob(name,jobConfig.getXml());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return e.toString();
        }
        return name;
    }
    public String createBlockDataJob(String Namespace,String podName,int height,String ChannelName) {
        Jenkinsfile jenkinsfile = new Jenkinsfile();
        jenkinsfile.setCpAndExplainBlockData(Namespace,podName,height,ChannelName);
        JobConfig jobConfig = new JobConfig(jenkinsfile.getScript());
        String name = "Query-"+ChannelName+"-BlockData";
        try {
            if (jenkins.getJob(name) == null) {
                jenkins.createJob(name,jobConfig.getXml());
            } else {
                jenkins.deleteJob(name);
                jenkins.createJob(name,jobConfig.getXml());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return e.toString();
        }
        return name;
    }
    public String createChaincodeInCli(String Namespace,String podName1,String podName2){
        Jenkinsfile jenkinsfile = new Jenkinsfile();
        jenkinsfile.setChaincodeInstall(Namespace,podName1,podName2);
        JobConfig jobConfig = new JobConfig(jenkinsfile.getScript());
        String name = "Create-"+Namespace+"-Chaincode-InCli";
        try {
            if (jenkins.getJob(name) == null) {
                jenkins.createJob(name,jobConfig.getXml());
            } else {
                jenkins.deleteJob(name);
                jenkins.createJob(name,jobConfig.getXml());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return e.toString();
        }
        return name;
    }
    public String createChaincodeInChannel(String Namespace,String podName1,String podName2,String peerPodName,String ChannelName,String git){
        Jenkinsfile jenkinsfile = new Jenkinsfile();
        jenkinsfile.setInstallChaincodeInChannle(Namespace,podName1,podName2,peerPodName,ChannelName,git);
        JobConfig jobConfig = new JobConfig(jenkinsfile.getScript());
        String name = "Create-"+Namespace+"-Chaincode-InChannel";
        try {
            if (jenkins.getJob(name) == null) {
                jenkins.createJob(name,jobConfig.getXml());
            } else {
                jenkins.deleteJob(name);
                jenkins.createJob(name,jobConfig.getXml());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return e.toString();
        }
        return name;
    }
    public void buildJob(String jobName){
        try {
            jenkins.getJob(jobName).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

