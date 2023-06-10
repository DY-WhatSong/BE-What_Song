package dy.whatsong.global.config;


import dy.whatsong.domain.member.entity.Member;
import dy.whatsong.domain.member.service.TokenService;
import dy.whatsong.global.filter.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    private final TokenService tokenService;
//    private final CorsFilter corsFilter;

    public static final String FRONT_URL = "http://localhost:3000";

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .httpBasic().disable()
                    .formLogin().disable()
    //                .addFilter(corsFilter);
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http
                    .authorizeRequests()
                        .antMatchers("/oauth/**",
                                                "/**",
                                                "/test",
                                                "/user/**",
                                                "/user/*",
                                                "/user/kakao/*",
                                                "/api/v1/healthcheck")
                        .permitAll()
                        .antMatchers("/api/**").authenticated()
                        .anyRequest().authenticated()
                    .and()
                        .exceptionHandling()
                    .and()
                        .addFilterBefore(
                                new JwtAuthenticationFilter(),
                                UsernamePasswordAuthenticationFilter.class
                        );

    }
}

