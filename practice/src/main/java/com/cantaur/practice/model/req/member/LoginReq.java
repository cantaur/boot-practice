package com.cantaur.practice.model.req.member;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginReq {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
