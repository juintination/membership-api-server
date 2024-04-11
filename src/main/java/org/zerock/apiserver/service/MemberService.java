package org.zerock.apiserver.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.apiserver.domain.Member;
import org.zerock.apiserver.dto.MemberDTO;
import org.zerock.apiserver.dto.MemberModifyDTO;

import java.util.stream.Collectors;

@Transactional
public interface MemberService {

    MemberDTO get(Long mno);

    Long getMno(String email) throws Exception;

    Long register(MemberDTO memberDTO) throws Exception;

    void modify(MemberModifyDTO modifyDTO) throws Exception;

    void remove(Long mno);

    default MemberDTO entityToDTO(Member member) {
        return new MemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList().stream()
                        .map(memberRole -> memberRole.name()).collect(Collectors.toList()));
    }

}
