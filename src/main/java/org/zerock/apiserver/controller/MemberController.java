package org.zerock.apiserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zerock.apiserver.dto.MemberDTO;
import org.zerock.apiserver.dto.MemberModifyDTO;
import org.zerock.apiserver.service.MemberService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{mno}")
    public MemberDTO get(@PathVariable("mno") Long mno) {
        return memberService.get(mno);
    }

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody MemberDTO dto) throws Exception {
        long mno = memberService.register(dto);
        return Map.of("MNO", mno);
    }

    @PutMapping("/{mno}")
    public Map<String, String> modify(@PathVariable("mno") Long mno, @RequestBody MemberModifyDTO dto) throws Exception {
        dto.setMno(mno);
        memberService.modify(dto);
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{mno}")
    public Map<String, String> remove(@PathVariable("mno") Long mno) {
        memberService.remove(mno);
        return Map.of("RESULT", "SUCCESS");
    }

}
