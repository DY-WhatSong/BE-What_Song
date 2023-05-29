package dy.whatsong.domain.oauth.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dy.whatsong.domain.oauth.domain.KakaoProfile;
import dy.whatsong.domain.oauth.domain.OAuthToken;
import dy.whatsong.global.constant.Properties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoOAuth2 {

    private final Gson gson;
    private final Properties.KakaoProperties kakaoProperties;

    public KakaoOAuth2(Properties.KakaoProperties kakaoProperties) {
        this.gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        this.kakaoProperties = kakaoProperties;
    }

    public KakaoProfile getUserInfo(String authorizedCode) {
        // 1. 인가코드 -> 액세스 토큰
        OAuthToken accessToken = getAccessToken(authorizedCode);

        // 2. 액세스 토큰 -> 카카오 사용자 정보
        KakaoProfile userInfo = getUserInfoByToken(accessToken.getAccess_token());

        return userInfo;
    }

    /**
     * 인가 코드를 통해서 access_token 발급받는 메서드
     * @param authorizedCode
     * @return OAuthToken
     */
    private OAuthToken getAccessToken(String authorizedCode) {
        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoProperties.getCLIENT_ID());
        params.add("redirect_uri", "http://localhost:8080/user/kakao/callback");
        params.add("code", authorizedCode);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        OAuthToken oAuthToken = gson.fromJson(response.getBody(), OAuthToken.class);

        return oAuthToken;
        // JSON -> 액세스 토큰 파싱
//        String tokenJson = response.getBody();
//        JsonObject jsonObject = gson.fromJson(tokenJson, JsonObject.class);
//        String accessToken = jsonObject.get("access_token").getAsString();
//        return accessToken;
    }

    /**
     * 카카오 서버에 접근해서 사용자의 정보를 받아오는 메서드
     * @param accessToken
     * @return KakaoProfile
     */
    private KakaoProfile getUserInfoByToken(String accessToken) {
        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        KakaoProfile kakaoProfile = gson.fromJson(response.getBody(), KakaoProfile.class);

        return kakaoProfile;

//        Long id = jsonObject.get("id").getAsLong();
//        String email = jsonObject.getAsJsonObject("kakao_account").get("email").getAsString();
//        String nickname = jsonObject.getAsJsonObject("properties").get("nickname").getAsString();
//        return new KakaoProfile(id, email, nickname);
    }

}
