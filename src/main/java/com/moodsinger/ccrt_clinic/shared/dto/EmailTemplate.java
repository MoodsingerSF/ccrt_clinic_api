package com.moodsinger.ccrt_clinic.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailTemplate {
  public String toEmail;
  public String subject;
  public String message;
  public String emailBodyType;
  public String bodyHtmlContent;

  public EmailTemplate(String toEmail, String subject, String message, String emailBodyType, String bodyHtmlContent) {
    this.toEmail = toEmail;
    this.bodyHtmlContent = bodyHtmlContent;
    this.subject = subject;
    this.message = message;
    this.emailBodyType = emailBodyType;
  }
}
