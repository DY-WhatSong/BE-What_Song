package dy.whatsong.global.filter.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dy.whatsong.domain.member.dto.MemberDto;
import dy.whatsong.domain.member.dto.TokenInfo;
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

        log.info("====================== REQUEST-URL : {} ====================== ", request.getRequestURI());
        if(request.getRequestURI().contains("/chat")) {

        } else if(request.getRequestURI().equals("/user/kakao/callback")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else if(request.getRequestURI().equals("/user/token/reissue") ||
                  request.getRequestURI().equals("/user/logout")) {
            String refreshToken = request.getHeader(jwtProperties.getREFRESH_TOKEN_HEADER());
            log.info("refreshToken : {}", refreshToken);
            isTokenValid(refreshToken, request, response);

            // DB에 리프레시 토큰 존재 여부 판별하여 로그아웃한 계정인지 체크
            isAccountLoggedOut(getMemberInfo(request).getRefreshToken(), response);
        } else {
            // 해당 회원 DB null 체크
            String authorizationCode = request.getHeader(jwtProperties.getACCESS_TOKEN_HEADER());
            if (!StringUtils.hasText(authorizationCode) || !authorizationCode.startsWith(jwtProperties.getTOKEN_PREFIX())) {
                log.info("TOKEN IS EMPTY OR NO WITH PREFIX");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                String accessToken = authorizationCode.replace(jwtProperties.getTOKEN_PREFIX(), "");;
                log.info("accessToken : {}", accessToken);
                isTokenValid(accessToken, request, response);

                // DB에 리프레시 토큰 존재 여부 판별하여 로그아웃한 계정인지 체크
                isAccountLoggedOut(getMemberInfo(request).getRefreshToken(), response);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isTokenValid(String token, HttpServletRequest request, HttpServletResponse response) {
        try {
            DecodedJWT verify = JWT.require(Algorithm.HMAC512(jwtProperties.getJWT_SECRET_KEY())).build().verify(token);
            String oauthId = verify.getClaim("oauthId").asString();
            String email = verify.getClaim("email").asString();

            request.setAttribute("oauthId", oauthId);
            request.setAttribute("email", email);
            return true;
        } catch (TokenExpiredException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new TokenExpiredException("token is expired.");
        } catch (JWTVerificationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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

    private MemberDto.MemberResponseDto getMemberInfo(HttpServletRequest request) {
        TokenInfo tokenInfoFromToken = TokenInfo.builder()
                .oauthId(request.getAttribute("oauthId").toString())
                .email(request.getAttribute("email").toString())
                .build();
        return memberService.getMember(tokenInfoFromToken.getOauthId(), tokenInfoFromToken.getEmail());
    }

    private void isAccountLoggedOut(String refreshToken, HttpServletResponse response) {
        if(!StringUtils.hasText(refreshToken)) {
            response.setStatus(440);
        }
    }
}
