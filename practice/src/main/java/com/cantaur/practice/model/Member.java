package com.cantaur.practice.model;

import lombok.Data;

@Data
public class Member {
    private Long memberUid;
    private String email;
    private String password;
    private String name;
    private String platform;
    private String secretKey;
}
