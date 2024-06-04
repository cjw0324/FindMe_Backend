package com.findme.FindMeBack.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;

    private final LoginFailureHandler loginFailureHandler;

//    private final JwtUtil jwtUtil;
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = new LoginSuccessHandler(null);
        this.loginFailureHandler = new LoginFailureHandler();
//        this.jwtUtil = new JwtUtil();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable());
        http.authorizeRequests()
//                .requestMatchers("/main","/api/auth/**","/login", "/oauth2/**").permitAll()  // Corrected method chain
                .requestMatchers("/main","/search/**","/info/**","/find/**","/api/auth/**","/login", "/oauth2/**").permitAll()  // Corrected method chain
                .anyRequest().authenticated()
                .and()
//                .oauth2Login(oauth2 -> oauth2.loginPage("/login").defaultSuccessUrl("http://localhost:3000/loginSuccess", true).successHandler(loginSuccessHandler).failureUrl("http://localhost:3000/loginFailure"))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler))
                .sessionManagement((httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return auth.build();
    }
}

