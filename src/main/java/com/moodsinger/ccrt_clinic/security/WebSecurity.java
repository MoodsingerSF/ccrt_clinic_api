package com.moodsinger.ccrt_clinic.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.moodsinger.ccrt_clinic.io.enums.Role;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity {
  @Autowired
  @Qualifier("customAuthenticationEntryPoint")
  AuthenticationEntryPoint authEntryPoint;

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
        .antMatchers(HttpMethod.GET, "/suggestions")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.GET, "/users/doctors")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.PUT, "/blogs/{blogId}/verification-status")
        .hasAnyAuthority(Role.ADMIN.name())

        .antMatchers(HttpMethod.GET, "/donations")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.PUT, "/donation-requests/{requestId}/request-status")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.PUT, "/donation-requests/{requestId}/completion-status")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.POST, "/fee-changing-requests")
        .hasAnyAuthority(Role.DOCTOR.name())
        .antMatchers(HttpMethod.PUT, "/fee-changing-requests")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.GET, "/fee-changing-requests")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.GET, "/blogs/{blogId}/related-blogs")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/donation-requests")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/users")
        .permitAll()
        .antMatchers(HttpMethod.PUT, "/blogs/{blogId}/num-times-read")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/otp")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/suggestions")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/tags/**")
        .permitAll()
        .antMatchers(HttpMethod.PUT, "/users/{userId}/password-reset")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/users/{userId}/password-reset-verification-code")
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
        .antMatchers(HttpMethod.GET, "/users/{userId}/rating").permitAll()
        .antMatchers(HttpMethod.GET, "/rating_criteria").permitAll()
        .antMatchers(HttpMethod.GET, "/blogs")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/blogs/{blogId}")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/misc/**")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/specializations/**")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/home-covers")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.DELETE, "/home-covers/{coverId}")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.PUT, "/home-covers/{coverId}")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.GET, "/home-covers")
        .permitAll()
        .antMatchers(HttpMethod.POST, SecurityConstants.LOG_IN_URL).permitAll().anyRequest()
        .authenticated().and().exceptionHandling()
        .authenticationEntryPoint(authEntryPoint).and()
        .addFilter(new AuthenticationFilter(authenticationManager(authenticationConfiguration)))
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
