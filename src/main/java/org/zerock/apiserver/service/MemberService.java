package org.zerock.apiserver.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.apiserver.domain.Member;
import org.zerock.apiserver.dto.MemberDTO;

@Transactional
public interface MemberService {

    MemberDTO get(Long mno);

    Long getMno(String email);

    Long register(MemberDTO memberDTO);

    void modify(MemberDTO modifyDTO);

    void remove(Long mno);

    void checkPassword(Long mno, String password);

    default MemberDTO entityToDTO(Member member) {
        return MemberDTO.builder()
                .mno(member.getMno())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .social(member.isSocial())
                .role(member.getMemberRole())
                .build();
    }

}
