package planner.igrus_planner.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import planner.igrus_planner.service.CustomOAuth2UserService;

@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2LoginSuccessHandler successHandler;

    public SecurityConfig(CustomOAuth2UserService oAuth2UserService, OAuth2LoginSuccessHandler successHandler) {
        this.oAuth2UserService = oAuth2UserService;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/error", "/favicon.ico", "/api/users/register", "/api/users/login").permitAll() // 인증 없이 접근 가능
                        .requestMatchers("/static/**", "/resources/**", "/css/**", "/js/**", "/images/**").permitAll() // 정적 리소스 허용
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // 사용자 정의 로그인 페이지
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService) // CustomOAuth2UserService 사용
                        )
                        .successHandler(successHandler) // 로그인 성공 핸들러
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            if (!request.getRequestURI().equals("/error")) {
                                response.sendRedirect("/login"); // 인증 실패 시 리다이렉트
                            }
                        })
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
