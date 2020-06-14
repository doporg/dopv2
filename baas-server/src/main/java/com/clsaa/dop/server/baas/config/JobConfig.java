package com.clsaa.dop.server.baas.config;

/**
 * 生成创建jenkins需要的xml
 *
 * @author Monhey
 */
public class JobConfig {
    private String script;
    private String xml;
    public JobConfig(String script){
        this.script = script;
//        this.xml = "<?xml version='1.1' encoding='UTF-8'?>\n" +
//                "<flow-definition plugin=\"workflow-job@2.36\">\n" +
//                "  <actions>\n" +
//                "    <org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobAction plugin=\"pipeline-model-definition@1.5.0\"/>\n" +
//                "    <org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobPropertyTrackerAction plugin=\"pipeline-model-definition@1.5.0\">\n" +
//                "      <jobProperties/>\n" +
//                "      <triggers/>\n" +
//                "      <parameters/>\n" +
//                "      <options/>\n" +
//                "    </org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobPropertyTrackerAction>\n" +
//                "  </actions>\n" +
//                "  <description></description>\n" +
//                "  <keepDependencies>false</keepDependencies>\n" +
//                "  <properties>\n" +
//                "    <hudson.plugins.jira.JiraProjectProperty plugin=\"jira@3.0.11\"/>\n" +
//                "  </properties>\n" +
//                "  <definition class=\"org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition\" plugin=\"workflow-cps@2.78\">\n" +
//                "    <script>#!/groovy\n" +
//                this.script +
//                "    </script>\n" +
//                "    <sandbox>true</sandbox>\n" +
//                "  </definition>\n" +
//                "  <triggers/>\n" +
//                "  <disabled>false</disabled>\n" +
//                "</flow-definition>";
        this.xml="<?xml version='1.1' encoding='UTF-8'?>\n" +
                "<project>\n" +
                "  <actions/>\n" +
                "  <description></description>\n" +
                "  <keepDependencies>false</keepDependencies>\n" +
                "  <properties>\n" +
                "    <hudson.plugins.jira.JiraProjectProperty plugin=\"jira@3.0.11\"/>\n" +
                "  </properties>\n" +
                "  <scm class=\"hudson.scm.NullSCM\"/>\n" +
                "  <canRoam>true</canRoam>\n" +
                "  <disabled>false</disabled>\n" +
                "  <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>\n" +
                "  <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>\n" +
                "  <triggers/>\n" +
                "  <concurrentBuild>false</concurrentBuild>\n" +
                "  <builders>\n" +
                "    <hudson.tasks.Shell>\n" +
                "      <command>\n"+
                this.script+"\n"+
                "</command>\n" +
                "    </hudson.tasks.Shell>\n" +
                "  </builders>\n" +
                "  <publishers/>\n" +
                "  <buildWrappers/>\n" +
                "</project>";
    }
    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }
}