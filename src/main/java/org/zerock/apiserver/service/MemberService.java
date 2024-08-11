package org.zerock.apiserver.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.apiserver.domain.Member;
import org.zerock.apiserver.dto.MemberDTO;

import java.util.stream.Collectors;

@Transactional
public interface MemberService {

    MemberDTO get(Long mno);

    Long getMno(String email);

    Long register(MemberDTO memberDTO);

    void modify(MemberDTO modifyDTO);

    void remove(Long mno);

    default MemberDTO entityToDTO(Member member) {
        return MemberDTO.builder()
                .mno(member.getMno())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .social(member.isSocial())
                .roleNames(member.getMemberRoleList().stream()
                        .map(Enum::name).collect(Collectors.toList()))
                .build();
    }

}
