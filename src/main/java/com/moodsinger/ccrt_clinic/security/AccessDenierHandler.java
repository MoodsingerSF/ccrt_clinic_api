package com.moodsinger.ccrt_clinic.security;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodsinger.ccrt_clinic.exceptions.model.ResponseMessage;

public class AccessDenierHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e)
      throws IOException, ServletException {
    ResponseMessage re = new ResponseMessage(HttpStatus.UNAUTHORIZED.toString(), "Authentication failed");
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    OutputStream responseStream = response.getOutputStream();
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(responseStream, re);
    responseStream.flush();
  }
}
