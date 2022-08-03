package com.moodsinger.ccrt_clinic.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.moodsinger.ccrt_clinic.io.enums.Role;

// import com.moodsinger.ccrt_clinic.io.enums.Role;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity {

  public WebSecurity() {
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    AuthenticationConfiguration authenticationConfiguration = httpSecurity
        .getSharedObject(AuthenticationConfiguration.class);

    httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
        .permitAll()
        .antMatchers(HttpMethod.GET, SecurityConstants.SIGN_UP_URL)
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.POST, "/users/admin")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.PUT, "/users/{userId}/role")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.PUT, "/users/{userId}/verification-status")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.GET, "/blogs")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/blogs/{blogId}")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/blogs/{blogId}/related-blogs")
        .permitAll()
        .antMatchers(HttpMethod.POST, SecurityConstants.LOG_IN_URL).permitAll().anyRequest()
        .authenticated().and().addFilter(new AuthenticationFilter(authenticationManager(authenticationConfiguration)))
        .addFilter(new AuthorizationFilter(authenticationManager(authenticationConfiguration)))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    return httpSecurity.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setAllowedHeaders(Arrays.asList("*"));

    Map<String, CorsConfiguration> mapping = new HashMap<>();
    mapping.put("/**", corsConfiguration);
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.setCorsConfigurations(mapping);
    return urlBasedCorsConfigurationSource;
  }
}
