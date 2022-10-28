package com.cantaur.practice.config.oAuth;

import com.cantaur.practice.config.resources.KakaoSocial;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "spring.kakao")
public class KakaoConfig {

    private String clientId;

    private String redirectUrl;

    @Bean(name="kakaoSocial")
    public KakaoSocial getKakaoSocial(){
        return new KakaoSocial(this.clientId, this.redirectUrl);
    }
}
