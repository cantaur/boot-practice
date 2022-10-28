package com.cantaur.practice.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService{
    /**
     * UserDetailService를 상속받은 CustomUserDetailsService 구현
     */

    String selectMemberAccessToken(String email) throws Exception;


}
