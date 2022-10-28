package com.cantaur.practice.service;

import com.cantaur.practice.common.encryptor.AES256Encryptor;
import com.cantaur.practice.common.encryptor.SHA256Encryptor;
import com.cantaur.practice.config.security.JwtTokenProvider;
import com.cantaur.practice.mapper.MemberMapper;
import com.cantaur.practice.model.member.Member;
import com.cantaur.practice.model.req.member.LoginReq;
import com.cantaur.practice.model.req.member.SocialMemberReq;
import com.cantaur.practice.model.resp.LoginResp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class MemberServiceImpl implements MemberService{

    private MemberMapper memberMapper;
    private final JwtTokenProvider jwtTokenProvider;


    public MemberServiceImpl(MemberMapper memberMapper,
                             JwtTokenProvider jwtTokenProvider){
        this.memberMapper = memberMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Integer validateMember(String email, String platform){
        return memberMapper.countMember(email, platform);
    }

    @Override
    public Long insertMember(SocialMemberReq socialMember) throws Exception {
        Long memberUid = socialMember.getMemberUid();
        Member member = new Member();
        //이메일 암호화
        socialMember.setEmail(AES256Encryptor.encrypt(socialMember.getEmail()));
        //비밀번호 암호화
        if(StringUtils.isNotEmpty(socialMember.getPassword())){
            socialMember.setPassword(SHA256Encryptor.encrypt(socialMember.getPassword())); //SHA256Encryptor 패스워드 암호화
        }

        if(socialMember.getMemberUid() > 0L){
            //업데이트 로직
        } else{
            //memberUid가 없으면 insert
            memberUid = memberMapper.insertMember(socialMember);
        }
        return memberUid;
    }



    @Override
    public LoginResp signIn(LoginReq loginReq, Locale locale) throws Exception{
        LoginResp loginResp = new LoginResp();
        String email = "";
        if (StringUtils.isNotEmpty(loginReq.getEmail())) {
            email = AES256Encryptor.encrypt(loginReq.getEmail());
        }
        Member member = memberMapper.findByEmail(email);

        //회원정보가 없을 경우
        if(ObjectUtils.isEmpty(member)){
            loginResp.setStatus(HttpStatus.UNAUTHORIZED.value());
            loginResp.setFailMsg("회원정보가 없습니다.");
            return loginResp;
        }


        //회원정보가 있을 경우
        final List<String> roles = Arrays.asList("ROLE_USER");
        loginResp.setStatus(HttpStatus.OK.value());
        String token = jwtTokenProvider.createToken(email,roles);
        loginResp.setToken(token);
        loginResp.setUsername(member.getName());

//            access token update
        this.updateAccessToken(token, email);

        return loginResp;

    }




    @Override
    public void updateAccessToken(String accessToken, String email) throws Exception{

        Member member = new Member();
        member.setAccessToken(accessToken);
        member.setEmail(email);
        memberMapper.updateAccessToken(member);


    }



    @Override
    public Member findAll(){
        return memberMapper.findbyUid(5L);
    }
}
