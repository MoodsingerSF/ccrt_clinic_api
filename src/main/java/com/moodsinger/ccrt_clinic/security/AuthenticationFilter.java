package com.moodsinger.ccrt_clinic.security;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodsinger.ccrt_clinic.SpringApplicationContext;
import com.moodsinger.ccrt_clinic.model.request.UserLoginRequestModel;
import com.moodsinger.ccrt_clinic.service.UserService;
import com.moodsinger.ccrt_clinic.shared.dto.UserDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  public AuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    try {
      UserLoginRequestModel userLoginRequestModel = new ObjectMapper().readValue(request.getInputStream(),
          UserLoginRequestModel.class);
      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(userLoginRequestModel.getEmail(), userLoginRequestModel.getPassword(),
              new ArrayList<>()));
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {
    String email = ((User) authResult.getPrincipal()).getUsername();
    UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
    UserDto userDto = userService.getUserByEmail(email);
    Key signingKey = Keys.hmacShaKeyFor(SecurityConstants.getSecurityToken().getBytes());
    String token = Jwts.builder().setSubject(userDto.getUserId())
        .setExpiration(new Date(System.currentTimeMillis() +
            SecurityConstants.EXPIRATION_TIME))
        .signWith(signingKey, SignatureAlgorithm.HS512).compact();
    response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);

    response.addHeader("UserId", userDto.getUserId());
  }

}
