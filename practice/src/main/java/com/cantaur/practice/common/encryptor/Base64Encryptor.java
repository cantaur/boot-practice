package com.cantaur.practice.common.encryptor;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Encryptor {
    public static String encode(final String str){
        if(StringUtils.isEmpty(str)) return "";
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    public static String decode(final String str){
        if(StringUtils.isEmpty(str)) return "";
        return new String(Base64.getDecoder().decode(str));
    }
}
