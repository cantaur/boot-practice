package com.cantaur.practice.controller;

import com.cantaur.practice.config.security.JwtTokenProvider;
import com.cantaur.practice.model.req.member.LoginReq;
import com.cantaur.practice.model.resp.LoginResp;
import com.cantaur.practice.model.resp.ResultResp;
import com.cantaur.practice.model.social.OAuthToken;
import com.cantaur.practice.model.social.SocialMember;
import com.cantaur.practice.model.social.SocialResp;
import com.cantaur.practice.service.CustomUserDetailsService;
import com.cantaur.practice.service.MemberService;
import com.cantaur.practice.service.SocialLoginService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    private CustomUserDetailsService customUserDetailsService;
    private SocialLoginService socialLoginService;
    private MemberService memberService;


    public LoginController(SocialLoginService socialLoginService,
                           MemberService memberService,
                           CustomUserDetailsService customUserDetailsService){
        this.socialLoginService = socialLoginService;
        this.memberService = memberService;
        this.customUserDetailsService = customUserDetailsService;

    }


    /**
     * 이메일 로그인
     * @return ResponseEntity<AuthResponse>
     */
    @PostMapping("/signin")
    public ResultResp<?> singIn(@RequestBody @Valid LoginReq loginReq, Locale locale) throws Exception {
        LoginResp loginResp= memberService.signIn(loginReq, locale);
        return new ResultResp<>(loginResp);
    }

    /**
     * KAKAO 소셜 로그인 URL 조회
     * @return ResponseEntity<AuthResponse>
     */
    @GetMapping("/kakao")
    public ResultResp<?> kakaoLogin() throws Exception {

        SocialResp socialResp = new SocialResp();
        socialResp.setKakaoUrl(socialLoginService.getKakaoUrl());

        return new ResultResp<>(socialResp);
    }


    /**
     * KAKAO Callback
     * @return ResponseEntity<AuthResponse>
     */
    @GetMapping("/kakao/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam String code, ModelAndView mav, HttpServletResponse response) throws Exception {

        OAuthToken oAuthToken = socialLoginService.getKaKaoInfo(code);
        SocialMember socialMember = socialLoginService.getKakaoProfile(oAuthToken);

        Integer count = memberService.validateMember(socialMember.getEmail(), socialMember.getSnsCode());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/join"));

        return new ResponseEntity<>(socialMember, headers, HttpStatus.MOVED_PERMANENTLY);

    }

}
