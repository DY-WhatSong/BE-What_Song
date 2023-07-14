package dy.whatsong.global.filter.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import dy.whatsong.global.constant.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // ngrok 로컬 테스트시 사용하는 코드
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PATCH, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization");

        log.info("requestURL : {}", request.getRequestURI());
        // 요청 헤더의 Authorization 항목 값을 가져와 jwtHeader 변수에 담음.
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        log.info(" Authorization : {}", authorization);
        if (authorization == null || !authorization.startsWith(jwtProperties.getTOKEN_PREFIX())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.replace(jwtProperties.getTOKEN_PREFIX(), "");

        Long memberSeq = null;

        try {
            memberSeq = verifyTokenAndGetMemberSeq(token);

        } catch (TokenExpiredException e) {
            log.info("TokenExpiredException");
            e.printStackTrace();
            request.setAttribute(jwtProperties.getHEADER_STRING(), "Token expired");
            return;
        } catch (JWTVerificationException e) {
            log.info("JWTVerificationException");
            e.printStackTrace();
            request.setAttribute(jwtProperties.getHEADER_STRING(), "Invalid token");
            return;
        }

        request.setAttribute("memberSeq", memberSeq);
//        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
//                user.getUsername(), null, user.getAuthorities()
//        );
//        SecurityContextHolder.getContext().setAuthentication(userToken);
//        chain.doFilter(request, response);
        filterChain.doFilter(request, response);
    }

    private Long verifyTokenAndGetMemberSeq(String token) throws JWTVerificationException {
        return JWT.require(Algorithm.HMAC512(jwtProperties.getJWT_SECRET_KEY()))
                .build()
                .verify(token)
                .getClaim("id")
                .asLong();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*");
            }
        };
    }

}
