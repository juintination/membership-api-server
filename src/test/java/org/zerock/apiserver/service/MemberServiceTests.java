package org.zerock.apiserver.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.apiserver.dto.MemberDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@Log4j2
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    @Test
    public void testGet() {
        Long mno = 1L;
        MemberDTO memberDTO = memberService.get(mno);
        log.info(memberDTO);
    }

    @Test
    public void testModify() throws Exception {
        Long mno = 2L;
        MemberDTO memberDTO = MemberDTO.builder()
                .mno(mno)
                .email("modifiedUser2@test.com")
                .password("1234")
                .nickname("modifiedUser2")
                .build();
        memberService.modify(memberDTO);

        MemberDTO result = memberService.get(mno);
        log.info(result);
    }

    @Test
    public void testRegister() throws Exception {
        String email = "testRemove@test.com";
        List<String> roleNames = new ArrayList<>(Collections.singletonList("USER"));
        MemberDTO memberDTO = MemberDTO.builder()
                .email(email)
                .password("1234")
                .nickname("tempUser")
                .roleNames(roleNames)
                .build();
        Long mno = memberService.register(memberDTO);
        log.info("mno: " + mno);
        log.info(memberService.get(mno));
    }

    @Test
    public void testRemove() throws Exception {
        String email = "testRemove@test.com";
        Long mno = memberService.getMno(email);
        memberService.remove(mno);
    }

}
