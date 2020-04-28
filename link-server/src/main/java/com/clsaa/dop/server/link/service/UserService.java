package com.clsaa.dop.server.link.service;

import com.clsaa.dop.server.link.feign.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserInterface userInterface;
}
