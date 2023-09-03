package dy.whatsong.global.config;


import dy.whatsong.global.constant.Properties;
import dy.whatsong.global.filter.jwt.CustomAuthenticationEntryPoint;
import dy.whatsong.global.filter.jwt.JwtAuthenticationFilter;
import dy.whatsong.global.filter.jwt.JwtExceptionHandlerFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Properties.JwtProperties jwtProperties;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
//                .addFilter(corsFilter);
                .sessionManagement()    // jwt 토큰을 사용하게 되면 세션을 사용하지 않는다고 서버에 명시적으로 선언해 주어야 합니다.
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.authorizeRequests()
                    .antMatchers("/oauth/**",
                                            "/**",
                                            "/test",
                                            "/test/**",
                                            "/user/**",
                                            "/user/*",
                                            "/user/kakao/*",
                                            "/user/kakao/callback",
                                            "/api/v1/healthcheck",
                                            "/user/login"
                    )
                        .permitAll()
                    .antMatchers("/api/v1/**").authenticated()
                    .antMatchers("/chat/**").permitAll()// chat으로 시작하는 리소스에 대한 접근 권한 설정
                    .anyRequest().authenticated()
//                .and()
//                    // 403 예외처리 핸들링 - 토큰에 대한 권한과 요청 권한이 달라짐
//                    .exceptionHandling()
//                    .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                    // 토큰이 없거나 위 변조된 경우
                    .exceptionHandling()
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint(jwtProperties))
                .and()
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(jwtExceptionHandlerFilter, JwtAuthenticationFilter.class);
    }
}
