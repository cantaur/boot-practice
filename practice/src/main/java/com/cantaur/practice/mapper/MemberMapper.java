package com.cantaur.practice.mapper;

import com.cantaur.practice.model.member.Member;
import com.cantaur.practice.model.req.member.SocialMemberReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    Integer countMember(@Param("email") String email, @Param("platform") String platform);
    Member findbyUid(@Param("memberUid") Long memberUid);
    Member findByEmail(@Param("email") String email);
    String selectMemberAccessToken(@Param("email") String email);

    Long insertMember(SocialMemberReq socialMemberReq);

    void updateAccessToken(Member member);
}
