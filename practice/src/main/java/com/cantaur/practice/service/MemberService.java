package com.cantaur.practice.service;

import com.cantaur.practice.model.member.Member;
import com.cantaur.practice.model.req.member.LoginReq;
import com.cantaur.practice.model.req.member.SocialMemberReq;
import com.cantaur.practice.model.resp.LoginResp;

import java.util.Locale;

public interface MemberService {
    Integer validateMember(String email, String platform);

    Long insertMember(SocialMemberReq socialMember) throws Exception;

    LoginResp signIn(LoginReq loginReq, Locale locale) throws Exception;

    void updateAccessToken(String accessToken, String email) throws Exception;

    Member findAll();
}
