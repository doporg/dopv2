package com.clsaa.dop.server.api;

import com.clsaa.dop.server.api.service.PolicyService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationTests {
    private PolicyService policyService;

    @Autowired
    public ApplicationTests(PolicyService policyService) {
        this.policyService = policyService;
    }


    @Test
	public void createServiceDiscoveryPolicyTest() {

	}

}
