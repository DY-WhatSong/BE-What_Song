package dy.whatsong.global.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import dy.whatsong.domain.member.service.oauth.OauthFilter;
import dy.whatsong.domain.member.service.oauth.OauthService;
import dy.whatsong.global.filter.jwt.CustomOauthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final ObjectMapper objectMapper;
    private final OauthService oauthService;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
//                .addFilter(corsFilter);
                .sessionManagement()    // jwt 토큰을 사용하게 되면 세션을 사용하지 않는다고 서버에 명시적으로 선언해 주어야 합니다.
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/oauth/**",
                        "/test",
                        "/test/**",
                        "/user/**",
                        "/user/*",
                        "/user/kakao/*",
                        "/user/kakao/callback",
                        "/healthcheck",
                        "/user/login",
                        "/oauth/**"
                )
                .permitAll()
                .antMatchers("/api/v1/**").authenticated()
                .antMatchers("/chat/**").permitAll()// chat으로 시작하는 리소스에 대한 접근 권한 설정
                .anyRequest().authenticated();
        http.addFilterAfter(customOauthFilter(), LogoutFilter.class);
        http.addFilterBefore(oauthAuthenticationFilter(), CustomOauthFilter.class);

//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encodePwd());
        provider.setUserDetailsService(oauthService);
        return new ProviderManager(provider);
    }

    @Bean
    public CustomOauthFilter customOauthFilter() throws Exception {
        CustomOauthFilter customOauthFilter
                = new CustomOauthFilter(objectMapper);
        customOauthFilter.setAuthenticationManager(authenticationManager());
        return customOauthFilter;
    }

    @Bean
    public OauthFilter oauthAuthenticationFilter() {
        OauthFilter oauthAuthenticationFilter = new OauthFilter(oauthService);
        return oauthAuthenticationFilter;
    }
}
