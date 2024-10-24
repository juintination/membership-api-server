package org.zerock.apiserver.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "profileImage")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String nickname;

    private boolean social;

    @Builder.Default
    private MemberRole memberRole = MemberRole.USER;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private ProfileImage profileImage;

    public void changeRole(MemberRole memberRole) {
        this.memberRole = memberRole;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeSocial(boolean social) {
        this.social = social;
    }

}
