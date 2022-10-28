package com.cantaur.practice.service;

import com.cantaur.practice.model.social.OAuthToken;
import com.cantaur.practice.model.social.SocialMember;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public interface SocialLoginService {
    String getKakaoUrl() throws Exception;

    OAuthToken getKaKaoInfo(String code) throws Exception;

    SocialMember getKakaoProfile(OAuthToken oAuthToken) throws Exception;

    void getOidcKakaoProfile(OAuthToken oAuthToken) throws Exception;

    String selectSocialResult(SocialMember socialMember, Model model, HttpServletRequest request);
}
