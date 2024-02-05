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
        KakaoUserRes kakaoUserRes = getUserInfo(oauthCodeRes.access_token());
        if (memberRepository.findByOauthId(kakaoUserRes.getId()).isPresent()) {
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

        return responseEntity.getBody();
    }

    public Long singUp(String accessToken, String refreshToken, String innerNickName) {
        KakaoUserRes kakaoUserRes = getUserInfo(accessToken);
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

        if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new UnauthorizedException();
        }

        OauthTokenValidRes response = responseEntity.getBody();
        if (response.expires_in() <= 10) {
            return false;
        }
        return true;
    }

    public void tokenReissue() {

    }
}
