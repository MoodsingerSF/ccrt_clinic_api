package com.moodsinger.ccrt_clinic.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorMessages;
import com.moodsinger.ccrt_clinic.exceptions.model.ErrorMessage;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authEx)
      throws IOException, ServletException {
    System.out.println("-------------------------------------unsuccess--------------------------");

    ErrorMessage errorMessageObj = new ErrorMessage("SIGN_IN_ERROR", "YOU ARE NOT AUTHORIZED.");
    if (authEx.getClass().equals(BadCredentialsException.class)) {
      errorMessageObj = new ErrorMessage(ExceptionErrorCodes.USERNAME_PASSWORD_MISMATCH.name(),
          ExceptionErrorMessages.USERNAME_PASSWORD_MISMATCH.getMessage());
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    } else if (authEx.getClass().equals(DisabledException.class)) {
      errorMessageObj = new ErrorMessage(ExceptionErrorCodes.REQUEST_PENDING_APPROVAL.name(),
          ExceptionErrorMessages.DOCTOR_REQUEST_PENDING_APPROVAL.getMessage());
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    ObjectMapper mapper = new ObjectMapper();
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    PrintWriter writer = response.getWriter();

    writer.println(
        mapper.writeValueAsString(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorMessageObj)));
    writer.flush();
  }

}
