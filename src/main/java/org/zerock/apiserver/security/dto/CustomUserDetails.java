package org.zerock.apiserver.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.zerock.apiserver.domain.Member;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class CustomUserDetails implements UserDetails {

    private Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return member.getMemberRoleList().stream().map(str ->
                new SimpleGrantedAuthority("ROLE_" + str)).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("mno", member.getMno());
        dataMap.put("email", member.getEmail());
        dataMap.put("nickname", member.getNickname());
        dataMap.put("social", member.isSocial());
        dataMap.put("roleNames", member.getMemberRoleList());
        return dataMap;
    }
}
