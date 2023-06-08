package dy.whatsong.domain.member.dto;

import dy.whatsong.domain.member.domain.KakaoProfile;
import dy.whatsong.domain.member.entity.MemberRole;
import dy.whatsong.domain.member.entity.SocialType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class MemberDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class MemberJoinReqDto {

        private Long memberSeq;
        private String email;
        private String nickname;
        private String innerNickname;
        private String imgURL;
        private String oauthId;
        private String refreshToken;
        private String profileMusic;
        private MemberRole memberRole;
        private SocialType socialType;

    }

}
