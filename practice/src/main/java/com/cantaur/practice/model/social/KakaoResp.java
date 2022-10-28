package com.cantaur.practice.model.social;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
public class KakaoResp {
    private BigInteger id;
    private String sub; //회원번호
    private String name; // 이름
    private String nickname; // 닉네임
    private String email; //이메일
    private Boolean email_verified;
    private String gender;
    private String birthdate;
    private String phone_number;
    private Boolean phone_number_verified;
    private String accessToken;
    private String refreshToken;
    private int refreshTokenExpiresIn;
    private String providerCode;


}