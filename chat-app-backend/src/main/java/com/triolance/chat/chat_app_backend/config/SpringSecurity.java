package com.triolance.chat.chat_app_backend.config;

import com.triolance.chat.chat_app_backend.JwtFilter.JwtFilter;
import com.triolance.chat.chat_app_backend.JwtUtils.JwtUtils;
import com.triolance.chat.chat_app_backend.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       UserServiceImpl userDetails) throws Exception {
        AuthenticationManagerBuilder authBuilder =

                http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetails)
                .passwordEncoder(passwordEncoder);

        return authBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
      http
              .cors(Customizer.withDefaults())
              .csrf(csrf -> csrf.disable())


              .authorizeHttpRequests(auth->auth
                      .requestMatchers("/signup").permitAll() // Explicitly permit signup
                      .requestMatchers("/login").permitAll()  // Add login if needed
                      .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                      .requestMatchers("/ws-chat/**").permitAll()
                      .requestMatchers("/room/**").authenticated()
                      .anyRequest().permitAll()

              )

              .sessionManagement(sm ->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
              .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
      return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration cfg = new CorsConfiguration();
    cfg.setAllowedOriginPatterns(List.of("*"));
    cfg.setAllowedOrigins(List.of("http://localhost:5174"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));                       // allow Authorization, Content‑Type, etc.
        cfg.setAllowCredentials(true);                              // send cookies/credentials if needed



        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cfg);

    return source;
}


}
