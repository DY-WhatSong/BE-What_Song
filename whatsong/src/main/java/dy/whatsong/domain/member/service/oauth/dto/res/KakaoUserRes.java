package dy.whatsong.domain.member.service.oauth.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoUserRes {
    private String id;
    private Properties properties;
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    public static class Properties {
        private String nickname;
        @JsonProperty("profile_image")
        private String profileImage;
    }

    @Getter
    public static class KakaoAccount {
        private Profile profile;
        private String email;

        @Getter
        public static class Profile {
            private String nickname;
            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }
    }
}
