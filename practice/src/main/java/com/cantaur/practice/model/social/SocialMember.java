package com.cantaur.practice.model.social;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SocialMember implements Serializable {

    private static final long serialVersionUID = -5350482293688562599L;

    private String email;

    private String snsCode;

    private String socialVerifyId;

    private String accessToken;

    private String refreshToken;

    private String pushToken;

    private String memberCode;
}
