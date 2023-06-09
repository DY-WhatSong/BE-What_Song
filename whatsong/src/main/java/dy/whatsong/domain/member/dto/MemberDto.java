package dy.whatsong.domain.member.dto;

import dy.whatsong.domain.member.entity.MemberRole;
import dy.whatsong.domain.member.entity.SocialType;
import lombok.*;

import javax.validation.constraints.NotNull;

public class MemberDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class MemberJoinReqDto {

        private Long memberSeq;
        @NotNull(message = "email field should not be null")
        private String email;
        @NotNull(message = "nickname field should not be null")
        private String nickname;
        private String innerNickname;
        private String imgURL;
        @NotNull(message = "oauthId field should not be null")
        private String oauthId;
        private String refreshToken;
        private String profileMusic;
        private MemberRole memberRole;
        private SocialType socialType;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class MemberJoinReqDto2 {

        @NotNull(message = "email field should not be null")
        private String email;
        @NotNull(message = "nickname field should not be null")
        private String nickname;
        @NotNull(message = "oauthId field should not be null")
        private String oauthId;

    }

}
