package com.moodsinger.ccrt_clinic.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodsinger.ccrt_clinic.SpringApplicationContext;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorMessages;
import com.moodsinger.ccrt_clinic.exceptions.model.ErrorMessage;
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

  // @Override
  // protected void unsuccessfulAuthentication(HttpServletRequest request,
  // HttpServletResponse response,
  // AuthenticationException authEx) throws IOException {
  // System.out.println("-------------------------------------unsuccess--------------------------");

  // ErrorMessage errorMessageObj = new ErrorMessage("SIGN_IN_ERROR", "YOU ARE NOT
  // AUTHORIZED.");
  // if (authEx.getClass().equals(BadCredentialsException.class)) {
  // errorMessageObj = new
  // ErrorMessage(ExceptionErrorCodes.USERNAME_PASSWORD_MISMATCH.name(),
  // ExceptionErrorMessages.USERNAME_PASSWORD_MISMATCH.getMessage());
  // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

  // } else if (authEx.getClass().equals(DisabledException.class)) {
  // errorMessageObj = new
  // ErrorMessage(ExceptionErrorCodes.REQUEST_PENDING_APPROVAL.name(),
  // ExceptionErrorMessages.DOCTOR_REQUEST_PENDING_APPROVAL.getMessage());
  // response.setStatus(HttpServletResponse.SC_FORBIDDEN);
  // }

  // ObjectMapper mapper = new ObjectMapper();
  // response.setContentType(MediaType.APPLICATION_JSON_VALUE);
  // response.setCharacterEncoding("UTF-8");
  // PrintWriter writer = response.getWriter();

  // writer.println(
  // mapper.writeValueAsString(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorMessageObj)));
  // writer.flush();
  // }

}
