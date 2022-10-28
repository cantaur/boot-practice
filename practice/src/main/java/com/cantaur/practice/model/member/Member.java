package com.cantaur.practice.model.member;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {
    private Long memberUid;
    private String email;
    private String password;
    private String birth;
    private String name;
    private String platform;
    private String secretKey;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime createdAt;
}
