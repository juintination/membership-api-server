package org.zerock.apiserver.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.apiserver.dto.MemberDTO;
import org.zerock.apiserver.util.MemberServiceException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@Log4j2
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    @Test
    public void testIsNull() {
        Assertions.assertNotNull(memberService);
        log.info(memberService.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testRegister() {
        String email = "sample@example.com";
        List<String> roleNames = new ArrayList<>(Collections.singletonList("USER"));
        MemberDTO memberDTO = MemberDTO.builder()
                .email(email)
                .password("1234")
                .nickname("SampleUser")
                .roleNames(roleNames)
                .build();
        Long mno = memberService.register(memberDTO);
        log.info("mno: " + mno);
        log.info(memberService.get(mno));
    }

    @Test
    public void testGet() {
        Long mno = 1L;
        MemberDTO memberDTO = memberService.get(mno);
        log.info(memberDTO);
    }

    @Test
    public void testModify() {
        Long mno = 1L;
        MemberDTO memberDTO = MemberDTO.builder()
                .mno(mno)
                .email("Modified@example.com")
                .password("NewPassword")
                .nickname("ModifiedUser")
                .build();
        memberService.modify(memberDTO);

        MemberDTO result = memberService.get(mno);
        log.info(result);
    }

    @Test
    public void testRemove() {
        String email = "sample@example.com";
        Long mno = memberService.getMno(email);
        memberService.remove(mno);
    }

    @Test
    public void testWithCopilot() {
        String email = "sample@example.com";
        Long mno = memberService.getMno(email);
        memberService.remove(mno);
        Assertions.assertThrows(MemberServiceException.class, () -> memberService.get(mno));
    }

}
