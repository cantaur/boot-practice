package com.cantaur.practice.service;

import com.cantaur.practice.config.resources.KakaoSocial;
import com.cantaur.practice.model.member.Member;
import com.cantaur.practice.model.member.MemberToken;
import com.cantaur.practice.model.social.KakaoProfile;
import com.cantaur.practice.model.social.KakaoResp;
import com.cantaur.practice.model.social.OAuthToken;
import com.cantaur.practice.model.social.SocialMember;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

@Slf4j
@Service
public class SocialLoginServiceImpl implements SocialLoginService {

    private KakaoSocial kakaoSocial;

    public SocialLoginServiceImpl(KakaoSocial kakaoSocial){
        this.kakaoSocial = kakaoSocial;
    }



    /**
     * 카카오 로그인 : 동의 화면을 호출하기 위한 URL을 생성하고 전달한다.
     * GET /oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code HTTP/1.1
     * Host: kauth.kakao.com
     * @return String
     */
    @Override
    public String getKakaoUrl() throws Exception {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("https://kauth.kakao.com/oauth/authorize");
        stringbuilder.append("?response_type=code&client_id=").append(kakaoSocial.getClientId()); //앱 REST API 키
        stringbuilder.append("&redirect_uri=").append(urlEncoding(kakaoSocial.getRedirectUrl())); //redirect_uri
        return stringbuilder.toString();
    }


    /**
     * 카카오 로그인 : 인가코드로 토큰발급을 요청한다.
     * POST /oauth/token HTTP/1.1
     * Host: kauth.kakao.com
     * Content-type: application/x-www-form-urlencoded;charset=utf-8
     * @return String
     */
    @Override
    public OAuthToken getKaKaoInfo(String code) throws Exception{
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");             // authorization_code로 고정
        params.add("client_id", kakaoSocial.getClientId());         //	앱 REST API 키
        params.add("redirect_uri", kakaoSocial.getRedirectUrl());   // 인가 코드가 리다이렉트된 URI
        params.add("code", code);                                   // 인가 코드 받기 요청으로 얻은 인가 코드

        Mono<String> accessTokenResponse = WebClient.builder()
                .baseUrl("https://kauth.kakao.com/oauth/token?")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.queryParams(params).build())
                .retrieve()
                .bodyToMono(String.class);

        OAuthToken oAuthToken = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            oAuthToken = mapper.readValue(accessTokenResponse.log().block(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return oAuthToken;
    }



    /**
     * 카카오 로그인 : 발급받은 ACCESS_TOKEN을 사용하여 현재 로그인한 사용자의 정보를 불러온다.
     * GET/POST /v2/user/me HTTP/1.1
     * Host: kapi.kakao.com
     * Authorization: Bearer ${ACCESS_TOKEN}/KakaoAK ${APP_ADMIN_KEY}
     * Content-type: application/x-www-form-urlencoded;charset=utf-8
     * @return String
     */
    @Override
    public SocialMember getKakaoProfile(OAuthToken oAuthToken) throws Exception{

        Mono<String> resourceProfileResponse = WebClient.builder()
                .baseUrl("https://kapi.kakao.com/v2/user/me")
                .defaultHeader("Authorization","Bearer "+ Objects.requireNonNull(oAuthToken).getAccess_token())
                .build()
                .get()
                .retrieve()
                .bodyToMono(String.class);

        KakaoProfile kakaoProfile = new KakaoProfile();
        ObjectMapper mapper = new ObjectMapper();

        try {
            kakaoProfile = mapper.readValue(resourceProfileResponse.log().block(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        SocialMember socialMember = new SocialMember();
        socialMember.setEmail(kakaoProfile.getKakao_account().getEmail());
        socialMember.setAccessToken(oAuthToken.getAccess_token());
        socialMember.setRefreshToken(oAuthToken.getRefresh_token());
        socialMember.setSnsCode("KAKAO");
        return socialMember;

    }

    /**
     * 카카오 로그인 : OpenId Connect 를 사용하여 현재 로그인한 사용자의 정보를 불러온다.
     * GET /v1/oidc/userinfo HTTP/1.1
     * Host: kapi.kakao.com
     * Authorization: Bearer ${ACCESS_TOKEN}
     * Content-type: application/x-www-form-urlencoded;charset=utf-8
     * @return String
     */
    @Override
    public void getOidcKakaoProfile(OAuthToken oAuthToken) throws Exception{
        Mono<String> resourceProfileResponse = WebClient.builder()
                .baseUrl("https://kapi.kakao.com/v1/oidc/userinfo")
                .defaultHeader("Authorization","Bearer "+ Objects.requireNonNull(oAuthToken).getAccess_token())
                .build()
                .get()
                .retrieve()
                .bodyToMono(String.class);

        KakaoResp kakaoProfile = new KakaoResp();
        ObjectMapper mapper = new ObjectMapper();
        kakaoProfile = mapper.readValue(resourceProfileResponse.log().block(), KakaoResp.class);

    }



    @Override
    public String selectSocialResult(SocialMember socialMember, Model model, HttpServletRequest request){
        Member member = new Member();
        MemberToken memberToken = new MemberToken();
        model.addAttribute("info", socialMember);
        model.addAttribute("member", member);
        model.addAttribute("result", 0000);
        return "join";

    }




    private static String urlEncoding(final String params) throws UnsupportedEncodingException {
        return URLEncoder.encode(params, "UTF-8");
    }
}
