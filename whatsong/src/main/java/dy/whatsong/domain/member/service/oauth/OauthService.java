package dy.whatsong.domain.member.service.oauth;

import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.repository.MemberRepository;
import dy.whatsong.domain.member.service.oauth.dto.OauthProperties;
import dy.whatsong.domain.member.service.oauth.dto.res.*;
import dy.whatsong.global.dto.ResponseEnvelope;
import dy.whatsong.global.exception.InvalidRequestAPIException;
import dy.whatsong.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class OauthService implements UserDetailsService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final OauthProperties oauthProperties;
    private final MemberRepository memberRepository;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


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

    public String singUp(String accessToken, String refreshToken, String innerNickName) {
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

        return saveMember.getOauthId();
    }

    public boolean validationForToken(String accessToken) {
        ResponseEntity<OauthTokenValidRes> responseEntity = getUserInfoByAccessToken(accessToken);

        OauthTokenValidRes response = responseEntity.getBody();
        if (response.expires_in() <= 10) {
            return false;
        }
        return true;
    }

    public MemberDetailRes getMe(String accessToken) {
        ResponseEntity<OauthTokenValidRes> responseEntity = getUserInfoByAccessToken(accessToken);

        OauthTokenValidRes response = responseEntity.getBody();
        Member member = memberRepository.findByOauthId(response.id().toString()).orElseThrow(InvalidRequestAPIException::new);

        return new MemberDetailRes(member);
    }

    private ResponseEntity<OauthTokenValidRes> getUserInfoByAccessToken(String accessToken) {
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

        return responseEntity;
    }

    public ReissueRes tokenReissue(String refreshToken) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "refresh_token");
        parameters.add("client_id", oauthProperties.getKakaoClientId());
        parameters.add("refresh_token", eliminateBearerPrefix(refreshToken));

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("charset", "utf-8");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        ResponseEntity<ReissueRes> responseEntity = restTemplate.exchange(
                oauthProperties.getReissueURI(),
                HttpMethod.POST,
                requestEntity,
                ReissueRes.class
        );

        ReissueRes reissueRes = responseEntity.getBody();

        assert reissueRes != null;
        if (reissueRes.refresh_token().isPresent()) {
            Member member = memberRepository.findByRefreshToken(refreshToken).orElseThrow(InvalidRequestAPIException::new);
            member.updateRefreshToken(reissueRes.refresh_token().get());
        }

        return reissueRes;
    }

    public void logout(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<LogoutRes> responseEntity = restTemplate.exchange(
                oauthProperties.getLogoutURI(),
                HttpMethod.POST,
                requestEntity,
                LogoutRes.class
        );

        LogoutRes logoutRes = responseEntity.getBody();

        Member member = memberRepository.findByOauthId(logoutRes.id().toString()).orElseThrow(InvalidRequestAPIException::new);
        member.updateRefreshToken(null);
    }

    private String eliminateBearerPrefix(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring("Bearer ".length());
        }
        return token;
    }

    public void error(HttpServletResponse response) throws IOException {
        ResponseEnvelope<?> envelope = ResponseEnvelope.of(
                "401",
                null,
                "인증되지 않은 사용자입니다."
        );
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(String.valueOf(envelope));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다."));

        return User.builder()
                .username(member.getEmail())
                .password(passwordEncoder.encode(member.getOauthId()))
                .roles("USER")
                .build();
    }
}
