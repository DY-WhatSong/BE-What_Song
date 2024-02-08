package dy.whatsong.domain.member.service.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class OauthFilter extends OncePerRequestFilter {

    private final OauthService oauthService;

    private final List<String> ignoreValidUrl = List.of("/oauth/callback", "/oauth/signup", "/oauth/reissue");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Request-uri:" + request.getRequestURI());
        
        if (!isIgnoreUrl(request.getRequestURI())) {
            String accessToken = request.getHeader("Authorization");
            if (!oauthService.validationForToken(accessToken)) {
                oauthService.error(response);
                return;
            }
        }


        filterChain.doFilter(request, response);
    }

    private boolean isIgnoreUrl(String url) {
        return ignoreValidUrl.contains(url);
    }
}
