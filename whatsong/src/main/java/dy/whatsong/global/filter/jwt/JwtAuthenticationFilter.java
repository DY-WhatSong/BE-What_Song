package dy.whatsong.global.filter.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import dy.whatsong.global.constant.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Properties.JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("requestURL="+request.getRequestURI());
        // 요청 헤더의 Authorization 항목 값을 가져와 jwtHeader 변수에 담음.
//        String jwtHeader = request.getHeader(jwtProperties.getTOKEN_PREFIX());
//
//        if (jwtHeader == null || !jwtHeader.startsWith(jwtProperties.getTOKEN_PREFIX())) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String token = jwtHeader.replace(jwtProperties.getTOKEN_PREFIX(), "");
//
//        Long memberSeq = null;
//
//        try {
//            memberSeq = verifyTokenAndGetMemberSeq(token);
//
//        } catch (TokenExpiredException e) {
//            e.printStackTrace();
//            request.setAttribute(jwtProperties.getHEADER_STRING(), "Token expired");
//
//        } catch (JWTVerificationException e) {
//            e.printStackTrace();
//            request.setAttribute(jwtProperties.getHEADER_STRING(), "Invalid token");
//        }
//
//        request.setAttribute("memberSeq", memberSeq);

        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PATCH, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization");

        System.out.println("response ContentType="+response.getContentType());
        filterChain.doFilter(request, response);
    }

    private Long verifyTokenAndGetMemberSeq(String token) throws JWTVerificationException {
        return JWT.require(Algorithm.HMAC512(jwtProperties.getJWT_SECRET_KEY()))
                .build()
                .verify(token)
                .getClaim("id")
                .asLong();
    }

}
