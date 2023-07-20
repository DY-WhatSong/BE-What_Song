package dy.whatsong.global.filter.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import dy.whatsong.domain.member.service.MemberService;
import dy.whatsong.global.constant.Properties;
import dy.whatsong.global.service.JwtService;
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
    private final JwtService jwtService;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // ngrok 로컬 테스트시 사용하는 코드
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PATCH, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization");

        log.info("====================== REQUEST-URL : {} ====================== ", request.getRequestURI());
        // Refresh 토큰 확인
        if(request.getRequestURI().equals("/user/kakao/callback")) {
            response.setStatus(HttpServletResponse.SC_OK);
//            filterChain.doFilter(request, response);
//            return;
        } else if(request.getRequestURI().equals("/user/token/reissue")) {
            String refreshToken = request.getHeader(jwtProperties.getREFRESH_TOKEN_HEADER());
            log.info(" Refresh-Token : {}", refreshToken);
            jwtService.isTokenValid(refreshToken, request, response);
//            filterChain.doFilter(request, response);
//            return;
        } else {
            // Access Token 확인
            // 요청 헤더의 Authorization 항목 값을 가져와 jwtHeader 변수에 담음.
            String authorizationCode = request.getHeader(jwtProperties.getACCESS_TOKEN_HEADER());

            log.info("[HEADER-AUTHORIZATION] : {}", authorizationCode);
            if (!org.springframework.util.StringUtils.hasText(authorizationCode) || !authorizationCode.startsWith(jwtProperties.getTOKEN_PREFIX())) {
                log.info("TOKEN IS EMPTY OR NO WITH PREFIX");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String accessToken = authorizationCode.replace(jwtProperties.getTOKEN_PREFIX(), "");
            log.info(" Access-Token : {}", accessToken);

            boolean tokenValid = jwtService.isTokenValid(accessToken, request, response);

//        Long oAuthId = null;
//        request.setAttribute("oAuthId", oAuthId);

            if(tokenValid) {
                memberService.getMember(request.getAttribute("oauthId").toString());
//            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
//                    mem, null, user.getAuthorities()
//            );
//            SecurityContextHolder.getContext().setAuthentication(userToken);
            }

        }
        filterChain.doFilter(request, response);

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
