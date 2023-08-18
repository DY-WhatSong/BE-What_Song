/*
package dy.whatsong.domain.member.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dy.whatsong.domain.member.domain.KakaoProfile;
import dy.whatsong.domain.member.domain.OAuthToken;
import dy.whatsong.domain.member.dto.MemberDto;
import dy.whatsong.domain.member.dto.TokenInfo;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.repository.MemberRepository;
import dy.whatsong.global.constant.Properties;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.auth0.jwt.JWT.require;

@Component
@Slf4j
public class TokenService {

    private final Gson gson;
    private final Properties.KakaoProperties kakaoProperties;
    private final Properties.JwtProperties jwtProperties;
    private final MemberRepository memberRepository;

    public TokenService(Properties.KakaoProperties kakaoProperties, Properties.JwtProperties jwtProperties, MemberRepository memberRepository) {
        this.gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        this.kakaoProperties = kakaoProperties;
        this.jwtProperties = jwtProperties;
        this.memberRepository = memberRepository;
    }

    public KakaoProfile.UsersInfo getUserInfo(String authorizedCode) {
        // 1. 인가코드 -> 액세스 토큰
        OAuthToken accessToken = getAccessToken(authorizedCode);

        // 2. 액세스 토큰 -> 카카오 사용자 정보
        KakaoProfile.UsersInfo userInfo = getUserInfoByToken(accessToken.getAccess_token());

        return userInfo;
    }

    */
/**
     * 인가 코드를 통해서 access_token 발급받는 메서드
     * @param authorizedCode
     * @return OAuthToken
     *//*

    public OAuthToken getAccessToken(String authorizedCode) {
        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoProperties.getCLIENT_ID());
        params.add("redirect_uri", kakaoProperties.getREDIRECT_URI());
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
    }

    */
/**
     * 카카오 서버에 접근해서 사용자의 정보를 받아오는 메서드
     * @param accessToken
     * @return KakaoProfile
     *//*

    public KakaoProfile getUserInfoByToken(String accessToken) {
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
        KakaoProfile.UsersInfo usersInfo = gson.fromJson(response.getBody(), KakaoProfile.UsersInfo.class);

        return usersInfo;

//        Long id = jsonObject.get("id").getAsLong();
//        String email = jsonObject.getAsJsonObject("kakao_account").get("email").getAsString();
//        String nickname = jsonObject.getAsJsonObject("properties").get("nickname").getAsString();
//        return new KakaoProfile(id, email, nickname);
    }

    */
/**
     * 토큰을 포함한 응답값 리턴 함수
     *//*

    public ResponseEntity getTokensResponse(Member member) {

        List<String> tokenList = getTokenList(member);

        log.info("member.getOauthId() : {} ", member.getOauthId());
        log.info("member.getEmail() : {}", member.getEmail());
        memberRepository.updateRefreshToken(member.getOauthId(), member.getEmail(), tokenList.get(1));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenList.get(0));
        headers.add("Refresh", "Bearer " + tokenList.get(1));

        log.info("Access-Token : {}", tokenList.get(0));
        log.info("Refresh-Token : {}", tokenList.get(1));

        return ResponseEntity.ok()
                .headers(headers)
                .body(convertToMemberDto(member));
    }

    */
/**
     * 토큰을 포함한 응답값 리턴 함수
     */
    public ResponseEntity<?> getReissusedTokensResponse(HttpServletRequest request) {

        String refreshToken = request.getHeader(jwtProperties.getREFRESH_TOKEN_HEADER());

        List<String> tokenList = reissueRefreshToken(refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "Bearer " + tokenList.get(0));
        headers.add("refresh", "Bearer " + tokenList.get(1));

        log.info("Reissused-Access-Token : {}", headers.get("authorization"));
        log.info("Reissused-Refresh-Token : {}", headers.get("refresh"));

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
    /**
     * 토큰을 포함한 응답값 리턴 함수
     */
    public ResponseEntity getKakaoProfileResponse(KakaoProfile.UsersInfo usersInfo) {

//        SingleResponse<KakaoProfile.UsersInfo> singleResponse = responseService.getSingleResponse(usersInfo);
        log.info("getKakaoProfileResponse.KakaoProfile : {}", usersInfo);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .body(usersInfo);
    }

    /**
     * refresh token 을 받아 access token 과 refresh token 재발급
     * @param refreshToken
     * @return access token과 refresh token List
     *//*

    public List<String> reissueRefreshToken(String refreshToken){

        List<String> tokenList = new ArrayList<>();

        String oauthId = getMemberOauthIdFromToken(refreshToken);

        Optional<Member> optionalMember = memberRepository.findByOauthId(oauthId);
        Member member = optionalMember.orElseThrow(() -> new IllegalArgumentException("Member not found"));

        tokenList.add(createToken(member));
        tokenList.add(createRefreshToken(member));

        return tokenList;
    }

    /**
     * 토큰 정보를 통해서 고유한 유저 식별 값을 가져오는 메서드
     * @param token 토큰
     * @return 유저정보({})
     */
    public String getOauthIdAndSocialType(String token) {
        String oauthId = this.getDecodedJWT(token).getClaim("oauthId").asString();
        String socialType = this.getDecodedJWT(token).getClaim("socialType").asString();
        if(StringUtils.hasText(oauthId) && StringUtils.hasText(socialType)) {
            return oauthId + ":" + socialType;
        }
        return null;
    }

    /**
     * 토큰 정보를 검증하는 메서드
     * @param token 토큰
     * @return 토큰 검증 여부
     *//*

    public boolean validateToken(String token) {
        if(StringUtils.hasText(getDecodedJWT(token).getClaim("oauthId").asString())) {
            return true;
        }
        return false;
    }

    /**
     * JWT토큰 생성하는 함수
     * @param member 사용자
     * @return 발급한 JWT 토큰
     */
    private String createToken(Member member) {
        return JWT.create()
                .withSubject(member.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getACCESS_TOKEN_EXPIRED_TIME()))
                .withClaim("oauthId", member.getOauthId())
                .withClaim("socialType", member.getSocialType().toString())
                .withClaim("email", member.getEmail())
                .withClaim("nickname", member.getNickname())
                .sign(Algorithm.HMAC512(jwtProperties.getJWT_SECRET_KEY()));
    }

    /**
     * refresh 토큰을 생성하는 함수
     * @param member 사용자
     * @return 발급한 refresh token
     */
    private String createRefreshToken(Member member) {
        return JWT.create()
                .withSubject(member.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ jwtProperties.getREFRESH_TOKEN_EXPIRED_TIME()))
                .withClaim("oauthId", member.getOauthId())
                .withClaim("socialType", member.getSocialType().toString())
                .withClaim("email", member.getEmail())
                .withClaim("nickname", member.getNickname())
                .sign(Algorithm.HMAC512(jwtProperties.getJWT_SECRET_KEY()));
    }

    private String getMemberOauthIdFromToken(String token) {
        return require(Algorithm.HMAC512(jwtProperties.getJWT_SECRET_KEY()))
                .build()
                .verify(token)
                .getClaim("oauthId")
                .asString();
    }

    public TokenInfo getTokenInfoFromToken(String token) {
        return TokenInfo.builder()
                .oauthId(getDecodedJWT(token).getClaim("oauthId").toString())
                .email(getDecodedJWT(token).getClaim("email").toString())
                .build();
    }

    private DecodedJWT getDecodedJWT(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(jwtProperties.getJWT_SECRET_KEY())).build().verify(token);
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw ex;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw ex;
        } catch (TokenExpiredException e) {
            throw new TokenExpiredException("Expired JWT token");
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw ex;
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw ex;
        }
    }

    private List<String> getTokenList(Member member) {
        List<String> tokenList = new ArrayList<>();

        tokenList.add(createToken(member));
        tokenList.add(createRefreshToken(member));

        return tokenList;
    }

    private MemberDto.MemberResponseDto convertToMemberDto(Member member) {

        return MemberDto.MemberResponseDto.builder()
                .memberSeq(member.getMemberSeq())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .innerNickname(member.getInnerNickname())
                .imgURL(member.getImgURL())
                .oauthId(member.getOauthId())
                .refreshToken(member.getRefreshToken())
                .profileMusic(member.getProfileMusic())
                .memberRole(member.getMemberRole())
                .socialType(member.getSocialType())
                .build();
    }
}
*/
