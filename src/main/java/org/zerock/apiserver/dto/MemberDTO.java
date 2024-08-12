package org.zerock.apiserver.dto;

import lombok.*;
import org.zerock.apiserver.domain.MemberRole;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private Long mno;

    private String email;

    private String password;

    private String nickname;

    private boolean social;

    private MemberRole role;

}
