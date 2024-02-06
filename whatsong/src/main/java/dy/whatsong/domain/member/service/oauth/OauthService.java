package dy.whatsong.domain.member.service.oauth;

import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.repository.MemberRepository;
import dy.whatsong.domain.member.service.oauth.dto.KakaoUserRes;
import dy.whatsong.domain.member.service.oauth.dto.OauthCodeRes;
import dy.whatsong.domain.member.service.oauth.dto.OauthProperties;
import dy.whatsong.domain.member.service.oauth.dto.OauthTokenValidRes;
import dy.whatsong.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
@Log4j2
public class OauthService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final OauthProperties oauthProperties;
    private final MemberRepository memberRepository;

    public OauthCodeRes getTokenInfoByResourceServer(String code) {

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", oauthProperties.getKakaoClientId());
        parameters.add("redirect_uri", oauthProperties.getKakaoRedirectUri());
        parameters.add("code", code);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("charset", "utf-8");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        ResponseEntity<OauthCodeRes> responseEntity = restTemplate.exchange(
                oauthProperties.getKakaoTokenUri(),
                HttpMethod.POST,
                requestEntity,
                OauthCodeRes.class
        );

        OauthCodeRes oauthCodeRes = responseEntity.getBody();

        assert oauthCodeRes != null;
        KakaoUserRes kakaoUserRes = getUserInfo(oauthCodeRes.access_token());

        if (responseEntity.getStatusCode() == UNAUTHORIZED || responseEntity.getStatusCode() == BAD_REQUEST) {
            throw new UnauthorizedException();
        }

        Optional<Member> findMember = memberRepository.findByOauthId(kakaoUserRes.getId());
        if (findMember.isPresent()) {
            oauthCodeRes = new OauthCodeRes(oauthCodeRes, kakaoUserRes.getId());
        }

        return oauthCodeRes;
    }

    private KakaoUserRes getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("charset", "utf-8");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<KakaoUserRes> responseEntity = restTemplate.exchange(
                oauthProperties.getKakaoUserInfoUri(),
                HttpMethod.GET,
                requestEntity,
                KakaoUserRes.class
        );

        if (responseEntity.getStatusCode() == UNAUTHORIZED) {
            throw new UnauthorizedException();
        }

        System.out.println(responseEntity);
        return responseEntity.getBody();
    }

    public Long singUp(String accessToken, String refreshToken, String innerNickName) {
        String accessToken1 = eliminateBearerPrefix(accessToken);
        System.out.println("!=" + accessToken1);
        KakaoUserRes kakaoUserRes = getUserInfo(accessToken1);

        Member member = new Member(kakaoUserRes.getKakaoAccount().getEmail(),
                kakaoUserRes.getProperties().getNickname(),
                innerNickName,
                kakaoUserRes.getKakaoAccount().getProfile().getProfileImageUrl(),
                kakaoUserRes.getId(),
                refreshToken);

        Member saveMember = memberRepository.save(member);

        return saveMember.getMemberSeq();
    }

    public boolean validationForToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<OauthTokenValidRes> responseEntity = restTemplate.exchange(
                oauthProperties.getTokenValidURI(),
                HttpMethod.GET,
                requestEntity,
                OauthTokenValidRes.class
        );

        if (responseEntity.getStatusCode() == UNAUTHORIZED) {
            throw new UnauthorizedException();
        }

        OauthTokenValidRes response = responseEntity.getBody();
        if (response.expires_in() <= 10) {
            return false;
        }
        return true;
    }

    public void tokenReissue(String refreshToken) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", oauthProperties.getKakaoClientId());
        parameters.add("refresh_token", refreshToken);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("charset", "utf-8");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        ResponseEntity<OauthCodeRes> responseEntity = restTemplate.exchange(
                oauthProperties.getReissueURI(),
                HttpMethod.POST,
                requestEntity,
                OauthCodeRes.class
        );

        System.out.println(responseEntity);
    }

    public void logout(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                oauthProperties.getLogoutURI(),
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        System.out.println(responseEntity);
    }

    private String eliminateBearerPrefix(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring("Bearer ".length());
        }
        return token;
    }
}
