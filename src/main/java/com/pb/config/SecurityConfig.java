package com.pb.config;


import com.pb.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserServiceImpl userServiceImpl;
    public SecurityConfig(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(request -> request
                    .requestMatchers("/", "/login", "/register", "/auth/register", "/cart", "products").permitAll()
                    .anyRequest().authenticated()
            )
            .userDetailsService(userServiceImpl)
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            .formLogin(form -> form
                    .loginPage("/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .failureUrl("/login?error=true")
                    .permitAll())
            .logout(logout -> logout
                    .logoutSuccessUrl("/login?logout=true")
                    .invalidateHttpSession(true)
                    .permitAll());
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
