package com.cantaur.practice.controller;

import com.cantaur.practice.model.member.Member;
import com.cantaur.practice.model.req.member.SocialMemberReq;
import com.cantaur.practice.model.resp.ResultResp;
import com.cantaur.practice.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }


    @PostMapping("/join")
    public ResultResp<?> signUp(@RequestBody SocialMemberReq socialMemberReq) throws Exception {
        return new ResultResp<>(memberService.insertMember(socialMemberReq));
    }
}
