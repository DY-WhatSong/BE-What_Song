package dy.whatsong.domain.member.domain;

import lombok.*;

public class KakaoProfile {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class UsersInfo {

        private String id;
//        private String synched_at;
        private String connected_at;
        private KakaoAccount kakao_account;
        private Properties properties;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class KakaoAccount {

        private Boolean profile_nickname_needs_agreement;
        private Profile profile;

        private Boolean has_email;
        private Boolean email_needs_agreement;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String  email;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Profile {

        private String nickname;
//        private String thumbnail_image_url;
//        private String profile_image_url;
//        private Boolean is_default_image;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Properties {

        private String nickname;

    }

}
