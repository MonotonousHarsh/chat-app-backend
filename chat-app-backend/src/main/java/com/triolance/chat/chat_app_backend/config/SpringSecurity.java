package com.triolance.chat.chat_app_backend.config;

import com.triolance.chat.chat_app_backend.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SpringSecurity {


    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       UserServiceImpl userDetails) throws Exception {
        AuthenticationManagerBuilder authBuilder =

                http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetails);

        return authBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
      http
              .cors(Customizer.withDefaults())
              .csrf(csrf -> csrf.disable())


              .authorizeHttpRequests(auth->auth
                      .requestMatchers("/room/**", "/user/**").authenticated()
                      .anyRequest().permitAll()
              )
              .sessionManagement(sm ->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS) );
      return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration cfg = new CorsConfiguration();
    cfg.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:5174", "http://localhost:5175", "http://localhost:5176"));
    cfg.addAllowedMethod(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS").toString());
    cfg.addAllowedHeader(List.of("*").toString());
    cfg.setAllowCredentials(true);


    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cfg);

    return source;
}


}
