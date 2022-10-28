package com.cantaur.practice.model.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoginResp implements Serializable {

    private static final long serialVersionUID = 3131408869359622601L;
    private String token;
    private String uid;
    private String username;
    private int status;
    private String failMsg;


}
