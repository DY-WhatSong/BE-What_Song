package dy.whatsong.domain.member.dto;

import dy.whatsong.domain.member.entity.MemberRole;
import dy.whatsong.domain.member.entity.SocialType;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
public class MemberResponseDto {

    @Getter
    @Builder
    public static class CheckResponse{
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
