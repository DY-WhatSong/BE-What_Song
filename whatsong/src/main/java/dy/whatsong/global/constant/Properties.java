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

        @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
        private String CLIENT_SECRET;

    }
}

