package dy.whatsong.domain.member.service.oauth.dto;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class OauthProperties {
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String kakaoTokenUri;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUri;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("https://kapi.kakao.com/v1/user/access_token_info")
    private String tokenValidURI;

    @Value("https://kauth.kakao.com/oauth/token")
    private String reissueURI;

    @Value("https://kapi.kakao.com/v1/user/logout")
    private String logoutURI;
}
