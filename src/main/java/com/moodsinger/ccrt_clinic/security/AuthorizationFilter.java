package com.moodsinger.ccrt_clinic.security;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.moodsinger.ccrt_clinic.SpringApplicationContext;
import com.moodsinger.ccrt_clinic.io.entity.RoleEntity;
import com.moodsinger.ccrt_clinic.service.UserService;
import com.moodsinger.ccrt_clinic.shared.dto.RoleDto;
import com.moodsinger.ccrt_clinic.shared.dto.UserDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class AuthorizationFilter extends BasicAuthenticationFilter {

  public AuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String authorizationHeader = request.getHeader(SecurityConstants.HEADER_STRING);
    if (authorizationHeader == null || !authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String authorizationHeader = request.getHeader(SecurityConstants.HEADER_STRING);
    if (authorizationHeader != null) {
      String token = authorizationHeader.replace(SecurityConstants.TOKEN_PREFIX, "");
      Key signingKey = Keys.hmacShaKeyFor(SecurityConstants.getSecurityToken().getBytes());

      String userId = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody()
          .getSubject();
      if (userId != null) {
        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserDto userDto = userService.getUserByUserId(userId);
        Set<RoleDto> roles = userDto.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (RoleDto role : roles) {
          authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }
        return new UsernamePasswordAuthenticationToken(userDto.getEmail(), null, authorities);
      }
      return null;
    }
    return null;
  }

}
