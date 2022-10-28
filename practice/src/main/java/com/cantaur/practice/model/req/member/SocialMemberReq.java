package com.cantaur.practice.model.req.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
public class SocialMemberReq {
    private Long memberUid = 0L;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
    private String birth;
    private String platform;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiredAt;


}
