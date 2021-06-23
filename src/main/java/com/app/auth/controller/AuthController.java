package com.app.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0/api/auth/")
public class AuthController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return "Hello From Spring Security";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String getToken(){
        return null;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(){

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(){}
}
