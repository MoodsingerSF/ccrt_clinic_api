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
// import org.springframework.security.web.authentication.AuthenticationFailureHandler;
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

    httpSecurity.cors().and().csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
        .permitAll()
        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .antMatchers(HttpMethod.GET, SecurityConstants.SIGN_UP_URL)
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.POST, "/users/admin")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.PUT, "/users/{userId}/role")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.PUT, "/users/{userId}/verification-status")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.GET, "/users/doctors")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.GET, "/blogs")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/blogs/{blogId}")
        .permitAll()
        .antMatchers(HttpMethod.PUT, "/blogs/{blogId}/verification-status")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.GET, "/blogs/{blogId}/related-blogs")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/otp")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/doctors/{userId}/schedule")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/users/doctors")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/otp/validation")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/slots/{slotId}/appointments")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/users/{userId}").permitAll()
        .antMatchers(HttpMethod.POST, SecurityConstants.LOG_IN_URL).permitAll().anyRequest()
        .authenticated().and().addFilter(new AuthenticationFilter(authenticationManager(authenticationConfiguration)))
        .addFilter(new AuthorizationFilter(authenticationManager(authenticationConfiguration)))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    // .and().formLogin()
    // .failureHandler(authenticationFailureHandler());
    return httpSecurity.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  // @Bean
  // public AuthenticationFailureHandler authenticationFailureHandler() {
  // return new CustomAuthenticationFailureHandler();
  // }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS"));
    corsConfiguration.setAllowCredentials(false);
    corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
    corsConfiguration.setExposedHeaders(Arrays.asList("Authorization", "UserId"));

    Map<String, CorsConfiguration> mapping = new HashMap<>();
    mapping.put("/**", corsConfiguration);
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.setCorsConfigurations(mapping);
    return urlBasedCorsConfigurationSource;
  }
}
