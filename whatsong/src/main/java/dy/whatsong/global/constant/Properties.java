package dy.whatsong.global.constant;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Properties {

    @Getter
    @Component
    public static class KakaoProperties {

        @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
        private String CLIENT_ID;

        @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
        private String REDIRECT_URI;

        @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
        private String CLIENT_SECRET;

    }

    @Getter
    @Component
    public static class JwtProperties {

        @Value("${jwt.secret}")
        private String JWT_SECRET_KEY;

        @Value("${jwt.expired.time}")
        private Long EXPIRED_TIME;

    }
}

