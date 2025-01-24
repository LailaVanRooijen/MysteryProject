package com.mystery.project.securityconfig;

import com.mystery.project.mainconfiguration.Routes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(
            authorize -> {
              authorize.requestMatchers(Routes.BASE_ROUTE).permitAll();
              authorize.anyRequest().authenticated();
            })
        .oauth2Login(Customizer.withDefaults())
        .formLogin(Customizer.withDefaults())
        .build();
  }
}
