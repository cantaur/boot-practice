package com.cantaur.practice.service;

import com.cantaur.practice.mapper.MemberMapper;
import com.cantaur.practice.model.Member;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    private MemberMapper memberMapper;
    @Autowired
    protected SqlSession sqlSession;

    public MemberServiceImpl(MemberMapper memberMapper){
        this.memberMapper = memberMapper;
    }

    @Override
    public Member findAll(){
        Long memberUid = 1L;
        return sqlSession.getMapper(MemberMapper.class).findbyUid(memberUid);
    }
}
