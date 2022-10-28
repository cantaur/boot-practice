package com.cantaur.practice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MainController {

    @GetMapping(value = "/index")
    public String index() {
        return "/index";
    }


    @GetMapping(value = "/login")
    public String login() {
        return "/login";
    }


    @GetMapping(value = "/join")
    public String join() {
        return "join";
    }




}
