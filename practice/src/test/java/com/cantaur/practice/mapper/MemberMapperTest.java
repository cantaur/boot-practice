package com.cantaur.practice.mapper;

import com.cantaur.practice.model.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    @DisplayName("Order Mapper Test")
    public void mybatis_Mapper_XML_테스트() throws Exception {

        // given
        Long seq = 1L;
        // when
        Member member = memberMapper.findbyUid(seq);
        // then
        final String name = member.getName();
        assertEquals("깐따", name);
    }

}