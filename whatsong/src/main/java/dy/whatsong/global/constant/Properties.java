package dy.whatsong.global.constant;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Properties {

    @Getter
    @Component
    public static class KakaoProperties {

        @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
        private String CLIENT_ID;

        @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
        private String REDIRECT_URI;

    }

    @Getter
    @Component
    public static class JwtProperties {

        @Value("${jwt.secret.key}")
        private String JWT_SECRET_KEY;

        @Value("${jwt.access.expiration}")
        private Long ACCESS_TOKEN_EXPIRED_TIME;

        @Value("${jwt.refresh.expiration}")
        private Long REFRESH_TOKEN_EXPIRED_TIME;

        @Value("${jwt.access.header}")
        private String ACCESS_TOKEN_HEADER;

        @Value("${jwt.refresh.header}")
        private String REFRESH_TOKEN_HEADER;

        private String TOKEN_PREFIX = "Bearer ";

        private String HEADER_STRING = "Authorization";
    }
}

