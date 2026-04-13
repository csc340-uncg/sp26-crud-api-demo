package com.csc340.crud_api.security;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private CustomStudentDetailsService studentDetailsService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
    requestCache.setMatchingRequestParameterName(null);
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests((authorize) -> authorize
            .dispatcherTypeMatchers(DispatcherType.FORWARD,
                DispatcherType.ERROR)
            .permitAll()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .requestMatchers("/static/**", "/css/**", "/profile-pictures/**", "/*.jpg", "/*.png", "/*.gif").permitAll()
            .requestMatchers("/", "/students", "/students/", "/home", "/students/add").permitAll()
            .requestMatchers("/students/delete/**", "/students/update/**").hasAuthority("MOD")
            .anyRequest().authenticated())
        .formLogin(Customizer.withDefaults())
        .exceptionHandling((x) -> x.accessDeniedPage("/403"))
        .logout(Customizer.withDefaults())
        .requestCache((cache) -> cache
            .requestCache(requestCache));

    return http.build();
  }

  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(studentDetailsService).passwordEncoder(
        passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}