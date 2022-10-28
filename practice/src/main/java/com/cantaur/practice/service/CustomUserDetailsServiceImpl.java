package com.cantaur.practice.service;

import com.cantaur.practice.mapper.MemberMapper;
import com.cantaur.practice.model.member.Member;
import com.cantaur.practice.model.member.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService{
    private MemberMapper memberMapper;


    public CustomUserDetailsServiceImpl(MemberMapper memberMapper){
        this.memberMapper = memberMapper;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            Member member = memberMapper.findByEmail(email);
            if(ObjectUtils.isEmpty(member)){
                throw new UsernameNotFoundException("Cannot find user = " +email);
            }
            final List<String> roles = Arrays.asList("ROLE_USER");
            return User
                    .builder()
                    .memberUid(member.getMemberUid())
                    .email(member.getEmail())
                    .passwd(member.getPassword())
                    .name(member.getName())
                    .roles(roles)
                    .build();
        }catch (Exception e){
            log.warn(e.getMessage()+e);
        }
        throw new UsernameNotFoundException("Cannot find user = " + email);
    }


    @Override
    public String selectMemberAccessToken(String email) throws Exception{
        String accessToken = memberMapper.selectMemberAccessToken(email);
        log.info("디비에서 꺼내온 토큰 -{}" , accessToken);
        return accessToken;

    }




}
