package com.cantaur.practice.config.security;

import com.cantaur.practice.common.encryptor.SHA256Encryptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.NoSuchAlgorithmException;

@Slf4j
public class SHA256PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            return SHA256Encryptor.encrypt(rawPassword.toString());
        } catch (NoSuchAlgorithmException e) {
            log.debug(e.getMessage() + " : {}", e);
        }
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            return SHA256Encryptor.encrypt(rawPassword.toString()).equals(encodedPassword);
        } catch (NoSuchAlgorithmException e) {
            log.debug(e.getMessage() + " : {}", e);
        }
        return false;
    }
}
