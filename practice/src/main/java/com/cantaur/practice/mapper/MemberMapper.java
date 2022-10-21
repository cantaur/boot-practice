package com.cantaur.practice.mapper;

import com.cantaur.practice.model.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    Member findbyUid(@Param("memberUid") Long memberUid);
}
