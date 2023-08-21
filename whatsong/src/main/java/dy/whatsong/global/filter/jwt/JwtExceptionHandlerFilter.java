package dy.whatsong.global.filter.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    public JwtExceptionHandlerFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        } catch (TokenExpiredException e) {
            log.info("TokenExpiredException: {}", e.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.", response, e);
        } catch (JWTVerificationException e) {
            log.info("JWTVerificationException: {}", e.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, "토큰 검증에 실패하였습니다.",response, e);
        } catch (RuntimeException e) {
            log.info("RuntimeException: {}", e.getMessage());
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "runtime exception exception handler filter)", response, e);
        }
    }

    public void setErrorResponse(HttpStatus status, String message, HttpServletResponse response, Throwable throwable){
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ExceptionResponse exceptionResponse = new ExceptionResponse(status.value(), message, throwable.getMessage());
        try{
            String json = objectMapper.writeValueAsString(exceptionResponse);
            response.getWriter().write(json);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
