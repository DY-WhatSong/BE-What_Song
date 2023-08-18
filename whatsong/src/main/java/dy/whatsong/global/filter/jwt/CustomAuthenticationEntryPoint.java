package dy.whatsong.global.filter.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import dy.whatsong.global.constant.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Properties.JwtProperties jwtProperties;
    private static ExceptionResponse exceptionResponse =
            new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), "UnAuthorized!!!", null);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
        log.info("[CustomAuthenticationEntryPoint.commence] ");
        log.error("UnAuthorizaed!!! message : " + authException.getMessage());
        //response에 넣기
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, exceptionResponse);
            os.flush();
        }

//        String exception = (String) request.getAttribute(jwtProperties.getHEADER_STRING());
//        String errorCode;
//
//        if (exception.equals("Token expired")) {
//            errorCode = "Token expired";
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, errorCode);
//        } else if (exception.equals("Invalid token")) {
//            errorCode = "Invalid token";
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, errorCode);
//        } else {
//            errorCode = "Unknown error";
//        }

//        setResponse(response, errorCode);
    }

}
