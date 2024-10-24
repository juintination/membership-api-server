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
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Test
    public void testIsNull() {
        Assertions.assertNotNull(memberRepository, "MemberRepository should not be null");
        Assertions.assertNotNull(profileImageRepository, "ProfileImageRepository should not be null");

        log.info(memberRepository.getClass().getName());
        log.info(profileImageRepository.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testInsertMember() {
        for (int i = 1; i <= 10 ; i++) {
            String email = "user" + i + "@test.com";

            Member member = Member.builder()
                    .email(email)
                    .password(passwordEncoder.encode(new Faker().internet().password()))
                    .nickname("USER" + i)
                    .memberRole(MemberRole.USER)
                    .build();

            if (i >= 5) {
                member.changeRole(MemberRole.MANAGER);
            }

            if (i >= 8) {
                member.changeRole(MemberRole.ADMIN);
            }

            if (!memberRepository.existsByEmail(email)) {
                memberRepository.save(member);
                ProfileImage profileImage = profileImageRepository.save(ProfileImage.builder()
                        .fileName(UUID.randomUUID() + "_" + "IMAGE" + i + ".png")
                        .member(member)
                        .build());
                member.changeProfileImage(profileImage);
                memberRepository.save(member);
            }
        }
    }

    @Test
    @Transactional
    public void testRead() {
        Long mno = 1L;
        Optional<Member> member = memberRepository.findById(mno);

        if (member.isPresent()) {
            log.info(member);
            log.info(member.get().getProfileImage());
        }
    }

    @Test
    public void testReadMemberByMno() {
        Long mno = 1L;
        Object result = memberRepository.getMemberByMno(mno);
        Object[] arr = (Object[]) result;
        log.info(Arrays.toString(arr));
    }

    @Test
    public void testReadByEmail() {
        String email = "user10@test.com";
        Member member = memberRepository.findByEmail(email);

        log.info(member);
        log.info(member.getMemberRole());
        log.info(member.getProfileImage());
    }

}
