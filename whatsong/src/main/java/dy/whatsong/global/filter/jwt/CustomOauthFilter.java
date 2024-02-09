package dy.whatsong.global.filter.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomOauthFilter extends AbstractAuthenticationProcessingFilter {

    private static final String CONTENT_TYPE = "application/json";
    private static final String HTTP_METHOD = "POST";
    private static final String DEFAULT_LOGIN_REQUEST_URL = "/oauth/callback";
    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD);

    private final ObjectMapper objectMapper;


    public CustomOauthFilter(ObjectMapper objectMapper) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        System.out.println("!!!!!!=" + request.getRequestURI());
        if (request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }

        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        System.out.println(messageBody);
        /*Map<String, String> usernamePasswordMap = objectMapper.readValue(messageBody, Map.class);

        String email = usernamePasswordMap.get();
        String password = usernamePasswordMap.get(PASSWORD_KEY);

*/
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("email", "password");//principal 과 credentials 전달

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
