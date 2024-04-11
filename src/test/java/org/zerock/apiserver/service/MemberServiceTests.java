package org.zerock.apiserver.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.apiserver.dto.MemberDTO;
import org.zerock.apiserver.dto.MemberModifyDTO;

import java.util.ArrayList;
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
        MemberModifyDTO memberModifyDTO = MemberModifyDTO.builder()
                .mno(mno)
                .email("modifiedUser2@test.com")
                .oldPassword("1234")
                .password("1234")
                .nickname("modifiedUser2")
                .build();
        memberService.modify(memberModifyDTO);

        MemberDTO memberDTO = memberService.get(mno);
        log.info(memberDTO);
    }

    @Test
    public void testRegister() throws Exception {
        String email = "testRemove@test.com";
        List<String> roleNames = new ArrayList<>();
        roleNames.add("USER");
        MemberDTO memberDTO = new MemberDTO(email, "1234", "tempUser", false, roleNames);
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
