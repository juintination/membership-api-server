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

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProfileImageServiceTests {

    @Autowired
    private ProfileImageService profileImageService;

    @Autowired
    private MemberService memberService;

    private final Faker faker = new Faker();

    @Test
    public void testIsNull() {
        Assertions.assertNotNull(profileImageService, "ProfileImageService should not be null");
        Assertions.assertNotNull(memberService, "MemberService should not be null");

        log.info(profileImageService.getClass().getName());
        log.info(memberService.getClass().getName());
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

        ProfileImageDTO profileImageDTO = ProfileImageDTO.builder()
                .fileName(UUID.randomUUID() + "_" + "IMAGE.png")
                .mno(mno)
                .build();

        Long pino = profileImageService.register(profileImageDTO);
        log.info(pino);

    }

    @Test
    public void testGet() {
        Long pino = 1L;
        ProfileImageDTO profileImageDTO = profileImageService.get(pino);
        Assertions.assertNotNull(profileImageDTO);
        log.info(profileImageDTO);
    }

    @Test
    public void testRemove() {
        Long pino = 1L;
        profileImageService.remove(pino);
        Assertions.assertThrows(CustomServiceException.class, () -> profileImageService.get(pino));
    }

}
