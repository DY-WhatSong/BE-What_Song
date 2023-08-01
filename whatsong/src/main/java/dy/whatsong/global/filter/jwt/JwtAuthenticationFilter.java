package dy.whatsong.global.filter.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dy.whatsong.domain.member.dto.TokenInfo;
import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.service.MemberService;
import dy.whatsong.domain.member.service.TokenService;
import dy.whatsong.global.constant.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Properties.JwtProperties jwtProperties;
    private final MemberService memberService;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // ngrok 로컬 테스트시 사용하는 코드
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PATCH, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization");

        log.info("====================== REQUEST-URL : {} ====================== ", request.getRequestURI());
        if(request.getRequestURI().equals("/user/kakao/callback")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            // 해당 회원 DB null 체크
            String refreshToken = request.getHeader(jwtProperties.getREFRESH_TOKEN_HEADER());
            TokenInfo tokenInfoFromToken = tokenService.getTokenInfoFromToken(refreshToken);
            Member member = memberService.getMember(tokenInfoFromToken.getOauthId(), tokenInfoFromToken.getEmail());

            if(!StringUtils.hasText(member.getRefreshToken())) {
                response.setStatus(440);
            } else if(request.getRequestURI().equals("/user/token/reissue")) {
                log.info(" Refresh-Token : {}", refreshToken);
                isTokenValid(refreshToken, request, response);
            } else {
                // Access Token 확인
                // 요청 헤더의 Authorization 항목 값을 가져와 jwtHeader 변수에 담음.
                String authorizationCode = request.getHeader(jwtProperties.getACCESS_TOKEN_HEADER());

                log.info("[HEADER-AUTHORIZATION] : {}", authorizationCode);
                if (!StringUtils.hasText(authorizationCode) || !authorizationCode.startsWith(jwtProperties.getTOKEN_PREFIX())) {
                    log.info("TOKEN IS EMPTY OR NO WITH PREFIX");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                String accessToken = authorizationCode.replace(jwtProperties.getTOKEN_PREFIX(), "");
                log.info(" Access-Token : {}", accessToken);

                if(isTokenValid(accessToken, request, response)) {
                    memberService.getMember(request.getAttribute("oauthId").toString());
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isTokenValid(String token, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            DecodedJWT verify = JWT.require(Algorithm.HMAC512(jwtProperties.getJWT_SECRET_KEY())).build().verify(token);
            String oauthId = verify.getClaim("oauthId").asString();
            request.setAttribute("oauthId", oauthId);
            return true;
        } catch (TokenExpiredException e) {
            throw new TokenExpiredException("token is expired.");
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("token is invalid.");
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperties.getHEADER_STRING());
        if (StringUtils.hasText(bearerToken) && StringUtils.startsWithIgnoreCase(bearerToken,
                "Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
