package org.zerock.apiserver.repository;

import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.apiserver.domain.Member;
import org.zerock.apiserver.domain.MemberRole;
import org.zerock.apiserver.domain.ProfileImage;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProfileImageRepositoryTests {

    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Faker faker = new Faker();

    @Test
    public void testIsNull() {
        Assertions.assertNotNull(profileImageRepository, "ProfileImageRepository should not be null");
        Assertions.assertNotNull(memberRepository, "MemberRepository should not be null");

        log.info(profileImageRepository.getClass().getName());
        log.info(memberRepository.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testInsertProfileImage() {

        Member member = memberRepository.save(Member.builder()
                .email(faker.internet().emailAddress())
                .password(passwordEncoder.encode(faker.internet().password()))
                .nickname(faker.name().name())
                .memberRole(MemberRole.USER)
                .social(false)
                .build());

        ProfileImage profileImage = profileImageRepository.save(ProfileImage.builder()
                .fileName(UUID.randomUUID() + "_" + "IMAGE.png")
                .member(member)
                .build());

        log.info(profileImage);

    }

    @Test
    @Transactional
    public void testRead() {

        Long pino = 1L;
        Optional<ProfileImage> result = profileImageRepository.findById(pino);
        ProfileImage profileImage = result.orElseThrow();

        Assertions.assertNotNull(profileImage);
        log.info(profileImage);
        log.info(profileImage.getMember());

    }

    @Test
    public void testReadByPino() {

        Long pino = 1L;
        Object result = profileImageRepository.getProfileImageByPino(pino);

        Object[] arr = (Object[]) result;
        log.info(Arrays.toString(arr));

    }

    @Test
    public void testReadByMno() {

        Long mno = 1L;
        Object result = profileImageRepository.getProfileImageByMno(mno);

        Object[] arr = (Object[]) result;
        log.info(Arrays.toString(arr));

    }

    @Test
    @Transactional
    public void testDelete() {
        Long pino = 1L;
        profileImageRepository.deleteById(pino);
        Optional<ProfileImage> result = profileImageRepository.findById(pino);
        Assertions.assertEquals(Optional.empty(), result, "ProfileImage should be deleted");
        log.info(result);
    }

}
