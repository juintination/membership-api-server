package org.zerock.apiserver.service;

import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.apiserver.domain.MemberRole;
import org.zerock.apiserver.dto.MemberDTO;
import org.zerock.apiserver.dto.ProfileImageDTO;
import org.zerock.apiserver.util.CustomServiceException;

import java.util.UUID;

@SpringBootTest
@Log4j2
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProfileImageService profileImageService;

    private final Faker faker = new Faker();

    @Test
    public void testIsNull() {
        Assertions.assertNotNull(memberService, "MemberService should not be null");
        Assertions.assertNotNull(profileImageService, "ProfileImageService should not be null");

        log.info(memberService.getClass().getName());
        log.info(profileImageService.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testRegister() {

        String email = "sample@example.com";
        if (memberService.existsByEmail(email)) {
            email = new Faker().internet().emailAddress();
        }

        MemberDTO memberDTO = MemberDTO.builder()
                .email(email)
                .password(faker.internet().password())
                .nickname(faker.name().name())
                .role(MemberRole.USER)
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
        MemberDTO result = memberService.get(mno);

        ProfileImageDTO profileImageDTO = ProfileImageDTO.builder()
                .fileName(UUID.randomUUID() + "_" + "IMAGE.png")
                .mno(mno)
                .build();
        Long pino = profileImageService.register(profileImageDTO);
        log.info(profileImageService.get(pino));

        MemberDTO memberDTO = MemberDTO.builder()
                .mno(mno)
                .email("Modified@example.com")
                .password("NewPassword")
                .nickname("ModifiedUser")
                .pino(pino)
                .social(true)
                .role(MemberRole.MANAGER)
                .build();
        memberService.modify(memberDTO);

        MemberDTO modifiedResult = memberService.get(mno);
        Assertions.assertNotEquals(result, modifiedResult);
        log.info(modifiedResult);
    }

    @Test
    public void testRemove() {
        String email = "sample@example.com";
        Long mno = memberService.getMno(email);
        memberService.remove(mno);
        Assertions.assertThrows(CustomServiceException.class, () -> memberService.get(mno));
    }

}
