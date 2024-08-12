package org.zerock.apiserver.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.apiserver.domain.Member;
import org.zerock.apiserver.domain.MemberRole;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testIsNull() {
        Assertions.assertNotNull(memberRepository);
        log.info(memberRepository.getClass().getName());
    }

    @Test
    @BeforeEach
    public void testInsertMember() {
        for (int i = 1; i <= 10 ; i++) {
            String email = "user" + i + "@test.com";
            Member member = Member.builder()
                    .email(email)
                    .password(passwordEncoder.encode("1234"))
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
            }
        }
    }

    @Test
    public void testRead() {
        Long mno = 1L;
        Optional<Member> member = memberRepository.findById(mno);

        if (member.isPresent()) {
            log.info(member);
        }
    }

    @Test
    public void testReadByEmail() {
        String email = "user10@test.com";
        Member member = memberRepository.findByEmail(email);

        log.info(member);
    }

    @Test
    public void testReadWithRoles() {
        String email = "user10@test.com";
        Member member = memberRepository.findByEmail(email);

        log.info(member);
        log.info(member.getMemberRole());
    }

}
