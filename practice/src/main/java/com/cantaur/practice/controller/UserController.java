package com.cantaur.practice.controller;

import com.cantaur.practice.model.member.Member;
import com.cantaur.practice.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    private MemberService memberService;

    public UserController(MemberService memberService){
        this.memberService = memberService;
    }


    @GetMapping("/list")
    public Member findAll(){
        return memberService.findAll();
    }

}
