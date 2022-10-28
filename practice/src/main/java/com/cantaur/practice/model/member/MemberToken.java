package com.cantaur.practice.model.member;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class MemberToken implements Serializable {
    private static final long serialVersionUID = 3465030627534980699L;
    private Long memberTokenId;
    private Long memberUid;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiredAt;
    private LocalDateTime createdAt;
}
