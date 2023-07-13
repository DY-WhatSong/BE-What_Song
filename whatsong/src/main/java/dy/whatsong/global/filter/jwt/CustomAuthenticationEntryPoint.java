package dy.whatsong.global.filter.jwt;

import dy.whatsong.global.constant.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Properties.JwtProperties jwtProperties;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         org.springframework.security.core.AuthenticationException authException)
            throws IOException, ServletException {
        String exception = (String) request.getAttribute(jwtProperties.getHEADER_STRING());
        String errorCode;

        if (exception.equals("Token expired")) {
            errorCode = "Token expired";
        } else if (exception.equals("Invalid token")) {
            errorCode = "Invalid token";
        } else {
            errorCode = "Unknown error";
        }

        setResponse(response, errorCode);
    }

    private void setResponse(HttpServletResponse response, String errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jwtProperties.getHEADER_STRING() + " : " + errorCode);
    }

}
