package com.cantaur.practice.model.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ResultResp<T> implements Serializable {

    private static final long serialVersionUID = 3791385192912790272L;

    private String code = "0";

    private String message;

    private T data;
    private List<T> dataList;

    public ResultResp(T data){
        this.data = data;
    }

    public ResultResp(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ResultResp(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


}
