package org.zerock.apiserver.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberModifyDTO {

    private Long mno;

    private String email;

    private String oldPassword;

    private String password;

    private String nickname;

}
