package com.clsaa.dop.server.baas.Service;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Service(value = "k8sClientService")
public class k8sClientService {
    /**
     * 设置默认Client
     */
    public void setK8sClient() throws IOException, ApiException {
        ApiClient client = new ClientBuilder().setBasePath("https://121.42.13.243:6443").setVerifyingSsl(false)
                .setAuthentication(new AccessTokenAuthentication("eyJhbGciOiJSUzI1NiIsImtpZCI6IkVoTHJsZ1YyUGk2el9EckJzY2trOUVseTB2ek90U0NpMWxkbktIQm9YNWcifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJhZG1pbi10b2tlbi1kaGJqbiIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50Lm5hbWUiOiJhZG1pbiIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6IjBhMDZkYmYwLTNmNjgtNGEyOC1hNWZhLTM4YjY4NDZjMmFjMCIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDprdWJlLXN5c3RlbTphZG1pbiJ9.lLFq2er2yIVcHmJNeVCBOXtPI1Dut5a_sugxk27J2a6fg4lEEnDFybaPsc8VS7sUFPMH_ZiEj7xgggO94HtWvDMP41m_eZzdoeqEh_KBPXphI3Z14V2Z4kbu2HBQD_eNJAj1sbx3n_RTbmRGzyF6Ks9_uE-5fk3pFSn_6NZvoK71g9DF0g4-5Z-XxALp83xfQV4JILp9arMVqEI5ywwvDBeogv4lt6ER_2qj4Kf55C7MsE3Si9h7yPoyn9xNgjyX0j-7t619VlBksKNpKw0nGFpbjTr6mNZWvIMhfHnsme2jcxMirNg-7isSH-axb3NLGrnjxWisSY6-xovBppDTmQ")).build();
        Configuration.setDefaultApiClient(client);
    }
}
