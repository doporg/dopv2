package com.clsaa.dop.server.link.feign;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@Component
public interface MessageInterface {

//    @PostMapping("/v2/email")
    public void addEmailV2(@RequestParam("from") String from,
                           @RequestParam("to") String to,
                           @RequestParam("subject") String subject,
                           @RequestParam("text") String text);
}
