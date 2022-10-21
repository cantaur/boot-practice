package com.cantaur.practice.controller;

import com.cantaur.practice.model.Member;
import com.cantaur.practice.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }




    @GetMapping("/list")
    public Member findAll(){
        return memberService.findAll();
    }
}
