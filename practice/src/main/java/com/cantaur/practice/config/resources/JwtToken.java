package com.cantaur.practice.config.resources;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JwtToken implements Serializable {

    private static final long serialVersionUID = -4849621207997566574L;

    private String secretKey;
    private int expireLength;

    public JwtToken(String secretKey, int expireLength){
        this.secretKey = secretKey;
        this.expireLength = expireLength;
    }
}

