package com.cantaur.practice.model.social;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class OAuthToken {
    private String code;
    private String id_token;
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;

    public OAuthToken(String data){
        this.code = data;
        this.id_token = data;
    }

    public static OAuthToken createKakao(String code){
        return new OAuthToken(code);
    }
}
