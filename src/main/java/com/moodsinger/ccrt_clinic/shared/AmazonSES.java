package com.moodsinger.ccrt_clinic.shared;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodsinger.ccrt_clinic.shared.dto.AppointmentDto;
import com.moodsinger.ccrt_clinic.shared.dto.EmailTemplate;
import com.moodsinger.ccrt_clinic.shared.dto.SlotDto;
import com.moodsinger.ccrt_clinic.shared.dto.UserDto;

@Component
public class AmazonSES {
  private final String FROM = "rafi1017623150@gmail.com";
  private final String SUBJECT = "[CCRT Clinic] Email Verification Code";
  private final String APPOINTMENT_SUBJECT = "[CCRT Clinic] Appointment Details";
  private final String REGISTRATION_REQUEST_ACCEPTANCE_EMAIL_SUBJECT = "[CCRT Clinic Registration] [SUCCESS] Your registration request has been accepted successfully.";
  private final String REGISTRATION_REQUEST_REJECTION_EMAIL_SUBJECT = "[CCRT Clinic Registration] [REJECTION] Your registration request has been rejected.";
  private final String PASSWORD_RESET_CODE_EMAIL_SUBJECT = "[CCRT Clinic] Password reset code.";
  private final String PRESCRIPTION_VIEW_CODE_EMAIL_SUBJECT = "[CCRT Clinic] Prescription view code.";
  WebClient webClient = WebClient.create("http://178.128.101.29:8002");

  public void sendVerificationEmail(String email, String code) {

    try {
      String endpoint = "/api/v1/send-email";
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");
      EmailTemplate emailTemplate = new EmailTemplate(email, SUBJECT, getTextBody(code), "html", getHTMLBody(code));
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonString;
      jsonString = objectMapper.writeValueAsString(emailTemplate);
      webClient.post()
          .uri(endpoint)
          .headers(httpHeaders -> httpHeaders.addAll(headers))
          .body(BodyInserters.fromValue(jsonString))
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

  }

  public void sendRegistrationRequestAcceptanceEmail(String email, String name) {

    try {
      String endpoint = "/api/v1/send-email";
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");
      EmailTemplate emailTemplate = new EmailTemplate(email, REGISTRATION_REQUEST_ACCEPTANCE_EMAIL_SUBJECT,
          getRegistrationRequestAcceptanceEmailText(name), "html", getRegistrationRequestAcceptanceEmailBody(name));
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonString;
      jsonString = objectMapper.writeValueAsString(emailTemplate);
      webClient.post()
          .uri(endpoint)
          .headers(httpHeaders -> httpHeaders.addAll(headers))
          .body(BodyInserters.fromValue(jsonString))
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  public void sendRegistrationRequestRejectionEmail(String email, String name) {

    try {
      String endpoint = "/api/v1/send-email";
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");
      EmailTemplate emailTemplate = new EmailTemplate(email, REGISTRATION_REQUEST_REJECTION_EMAIL_SUBJECT,
          getRegistrationRequestRejectionEmailText(name), "html", getRegistrationRequestRejectionEmailBody(name));
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonString;
      jsonString = objectMapper.writeValueAsString(emailTemplate);
      webClient.post()
          .uri(endpoint)
          .headers(httpHeaders -> httpHeaders.addAll(headers))
          .body(BodyInserters.fromValue(jsonString))
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  public void sendPasswordResetCode(UserDto userDto, String code) {

    try {
      String email = userDto.getEmail();
      String fullName = userDto.getFirstName() + " " + userDto.getLastName();
      String endpoint = "/api/v1/send-email";
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");
      EmailTemplate emailTemplate = new EmailTemplate(email, PASSWORD_RESET_CODE_EMAIL_SUBJECT,
          getPrescriptionViewCodeEmailText(fullName, code), "html", getPrescriptionViewCodeEmailBody(fullName, code));
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonString;
      jsonString = objectMapper.writeValueAsString(emailTemplate);
      webClient.post()
          .uri(endpoint)
          .headers(httpHeaders -> httpHeaders.addAll(headers))
          .body(BodyInserters.fromValue(jsonString))
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  public void sendPrescriptionViewCode(String email, String name, String code) {

    try {

      String endpoint = "/api/v1/send-email";
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");
      EmailTemplate emailTemplate = new EmailTemplate(email, PRESCRIPTION_VIEW_CODE_EMAIL_SUBJECT,
          getPasswordResetCodeEmailText(name, code), "html", getPasswordResetCodeEmailBody(name, code));
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonString;
      jsonString = objectMapper.writeValueAsString(emailTemplate);
      webClient.post()
          .uri(endpoint)
          .headers(httpHeaders -> httpHeaders.addAll(headers))
          .body(BodyInserters.fromValue(jsonString))
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  public void sendMeetingLink(String doctorEmail, String patientEmail, String code, String link) {

    try {

      String endpoint = "/api/v1/send-email";
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");
      EmailTemplate emailTemplate = new EmailTemplate(patientEmail, APPOINTMENT_SUBJECT,
          getMeetingTextBody(code, link, true), "html", getMeetingHTMLBody(code, link, true));
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonString;
      jsonString = objectMapper.writeValueAsString(emailTemplate);
      webClient.post()
          .uri(endpoint)
          .headers(httpHeaders -> httpHeaders.addAll(headers))
          .body(BodyInserters.fromValue(jsonString))
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    try {

      String endpoint = "/api/v1/send-email";
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");
      EmailTemplate emailTemplate = new EmailTemplate(doctorEmail, APPOINTMENT_SUBJECT,
          getMeetingTextBody(code, link, true), "html", getMeetingHTMLBody(code, link, true));
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonString;
      jsonString = objectMapper.writeValueAsString(emailTemplate);
      webClient.post()
          .uri(endpoint)
          .headers(httpHeaders -> httpHeaders.addAll(headers))
          .body(BodyInserters.fromValue(jsonString))
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  public void sendAppointmentCancellationEmail(AppointmentDto appointmentDto) {
    UserDto patient = appointmentDto.getPatient();
    UserDto doctor = appointmentDto.getDoctor();
    String patientEmail = patient.getEmail();
    String doctorEmail = doctor.getEmail();
    String patientName = patient.getFirstName() + " " + patient.getLastName();
    String doctorName = doctor.getFirstName() + " " + doctor.getLastName();

    try {

      String endpoint = "/api/v1/send-email";
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");
      EmailTemplate emailTemplate = new EmailTemplate(patientEmail, APPOINTMENT_SUBJECT,
          getAppointmentCancellationEmailText(patientName, appointmentDto), "html",
          getAppointmentCancellationEmailBody(patientName, appointmentDto));
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonString;
      jsonString = objectMapper.writeValueAsString(emailTemplate);
      webClient.post()
          .uri(endpoint)
          .headers(httpHeaders -> httpHeaders.addAll(headers))
          .body(BodyInserters.fromValue(jsonString))
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    try {

      String endpoint = "/api/v1/send-email";
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");
      EmailTemplate emailTemplate = new EmailTemplate(doctorEmail, APPOINTMENT_SUBJECT,
          getAppointmentCancellationEmailText(doctorName, appointmentDto), "html",
          getAppointmentCancellationEmailBody(patientName, appointmentDto));
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonString;
      jsonString = objectMapper.writeValueAsString(emailTemplate);
      webClient.post()
          .uri(endpoint)
          .headers(httpHeaders -> httpHeaders.addAll(headers))
          .body(BodyInserters.fromValue(jsonString))
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

  }

  private String getAppointmentCancellationEmailBody(String name, AppointmentDto appointmentDto) {
    SlotDto slot = appointmentDto.getSlot();
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("<div>");
    stringBuilder.append("<p>Dear ");
    stringBuilder.append(name);
    stringBuilder.append(",</p>");

    stringBuilder.append("</br>");
    stringBuilder.append("<p>");
    stringBuilder.append(
        "Your appointment on " + appointmentDto.getDate() + " at " + slot.getStartTime().toString() + " to "
            + slot.getEndTime().toString() + " has been cancelled.");
    stringBuilder.append("</p>");
    stringBuilder.append("</br>");
    stringBuilder.append("<p>Thanks,</p>");
    stringBuilder.append("</br>");
    stringBuilder.append("<p>CCRT Clinic</p>");
    stringBuilder.append("</div>");
    return stringBuilder.toString();
  }

  private String getAppointmentCancellationEmailText(String name, AppointmentDto appointmentDto) {
    SlotDto slot = appointmentDto.getSlot();

    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("Dear ");
    stringBuilder.append(name);
    stringBuilder.append(",\n");
    stringBuilder.append(
        "Your appointment on " + appointmentDto.getDate() + " at " + slot.getStartTime().toString() + " to "
            + slot.getEndTime().toString() + " has been cancelled.");
    stringBuilder.append("Thanks\n");
    stringBuilder.append("CCRT Clinic");
    return stringBuilder.toString();
  }

  private String getRegistrationRequestAcceptanceEmailBody(String name) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("<div>");
    stringBuilder.append("<p>Dear ");
    stringBuilder.append(name);
    stringBuilder.append(",</p>");

    stringBuilder.append("</br>");
    stringBuilder.append("<p>");
    stringBuilder.append(
        "Your registration request as a doctor on CCRT Clinic has been accepted. You can now log into your account and see patients.");
    stringBuilder.append("</p>");
    stringBuilder.append("</br>");
    stringBuilder.append("<p>Thanks,</p>");
    stringBuilder.append("</br>");
    stringBuilder.append("<p>CCRT Clinic</p>");
    stringBuilder.append("</div>");
    return stringBuilder.toString();
  }

  private String getRegistrationRequestAcceptanceEmailText(String name) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("Dear ");
    stringBuilder.append(name);
    stringBuilder.append(",\n");
    stringBuilder.append(
        "Your registration request as a doctor on CCRT Clinic has been accepted. You can now log into your account and see patients.");
    stringBuilder.append("Thanks\n");
    stringBuilder.append("CCRT Clinic");
    return stringBuilder.toString();
  }

  private String getMeetingHTMLBody(String code, String link, boolean sendCode) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("<div>");
    if (sendCode == true) {
      stringBuilder.append("<p>Your secret code is </p>");
      stringBuilder.append("</br>");
      stringBuilder.append("<h1>");
      stringBuilder.append(code);
      stringBuilder.append("</h1>");
      stringBuilder.append("</br>");
      stringBuilder.append(
          "<p>Do not share this code with anyone until doctor asks you about the code after the meeting ends. </p>");
      stringBuilder.append("</br>");
    }
    stringBuilder.append("<p>Your meeting link is </p>");
    stringBuilder.append("</br>");
    stringBuilder.append("<p>" + link + "</p>");
    stringBuilder.append("</br>");
    stringBuilder.append("</div>");
    return stringBuilder.toString();
  }

  private String getMeetingTextBody(String code, String link, boolean sendCode) {
    StringBuilder stringBuilder = new StringBuilder("");
    if (sendCode == true) {
      stringBuilder.append("Your secret code is ");
      stringBuilder.append(code);
      stringBuilder.append("\n");
      stringBuilder
          .append("Do not share this code with anyone until doctor asks you about the code after the meeting ends.\n");
    }
    stringBuilder.append("Your meeting link is \n");
    stringBuilder.append(link);

    return stringBuilder.toString();
  }

  private String getHTMLBody(String code) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("<div>");
    stringBuilder.append("<p>Your email verification code is </p>");
    stringBuilder.append("</br>");
    stringBuilder.append("<h1>");
    stringBuilder.append(code);
    stringBuilder.append("</h1>");
    stringBuilder.append("</div>");
    return stringBuilder.toString();
  }

  private String getTextBody(String code) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("Your email verification code is ");
    stringBuilder.append(code);
    return stringBuilder.toString();
  }

  private String getRegistrationRequestRejectionEmailBody(String name) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("<div>");
    stringBuilder.append("<p>Dear ");
    stringBuilder.append(name);
    stringBuilder.append(",</p>");

    stringBuilder.append("</br>");
    stringBuilder.append("<p>");
    stringBuilder.append(
        "We are very sorry to inform you that your registration request as a doctor on CCRT Clinic has been <h3>rejected</h3>.");
    stringBuilder.append("</p>");
    stringBuilder.append("</br>");
    stringBuilder.append("<p>Thanks,</p>");
    stringBuilder.append("</br>");
    stringBuilder.append("<p>CCRT Clinic</p>");
    stringBuilder.append("</div>");
    return stringBuilder.toString();
  }

  private String getRegistrationRequestRejectionEmailText(String name) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("Dear ");
    stringBuilder.append(name);
    stringBuilder.append(",\n");
    stringBuilder.append(
        "We are very sorry to inform you that your registration request as a doctor on CCRT Clinic has been rejected.");
    stringBuilder.append("Thanks\n");
    stringBuilder.append("CCRT Clinic");
    return stringBuilder.toString();
  }

  private String getPasswordResetCodeEmailBody(String name, String code) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("<div>");
    stringBuilder.append("<p>Dear ");
    stringBuilder.append(name);
    stringBuilder.append(",</p>");

    stringBuilder.append("</br>");
    stringBuilder.append("<p>");
    stringBuilder.append(
        "Your password reset code is");
    stringBuilder.append("</p>");
    stringBuilder.append("</br>");
    stringBuilder.append("<h1>");
    stringBuilder.append(
        code);
    stringBuilder.append("</h1>");
    stringBuilder.append("</br>");

    stringBuilder.append("<p>Thanks,</p>");
    stringBuilder.append("</br>");
    stringBuilder.append("<p>CCRT Clinic</p>");
    stringBuilder.append("</div>");
    return stringBuilder.toString();
  }

  private String getPasswordResetCodeEmailText(String name, String code) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("Dear ");
    stringBuilder.append(name);
    stringBuilder.append(",\n");
    stringBuilder.append(
        "Your password reset code is \n");
    stringBuilder.append(
        code);
    stringBuilder.append("Thanks\n");
    stringBuilder.append("CCRT Clinic");
    return stringBuilder.toString();
  }

  private String getPrescriptionViewCodeEmailBody(String name, String code) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("<div>");
    stringBuilder.append("<p>Dear ");
    stringBuilder.append(name);
    stringBuilder.append(",</p>");

    stringBuilder.append("</br>");
    stringBuilder.append("<p>");
    stringBuilder.append(
        "Your prescription view code is");
    stringBuilder.append("</p>");
    stringBuilder.append("</br>");
    stringBuilder.append("<h1>");
    stringBuilder.append(
        code);
    stringBuilder.append("</h1>");
    stringBuilder.append("</br>");

    stringBuilder.append("<p>Thanks,</p>");
    stringBuilder.append("</br>");
    stringBuilder.append("<p>CCRT Clinic</p>");
    stringBuilder.append("</div>");
    return stringBuilder.toString();
  }

  private String getPrescriptionViewCodeEmailText(String name, String code) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("Dear ");
    stringBuilder.append(name);
    stringBuilder.append(",\n");
    stringBuilder.append(
        "Your password reset code is \n");
    stringBuilder.append(
        code);
    stringBuilder.append("Thanks\n");
    stringBuilder.append("CCRT Clinic");
    return stringBuilder.toString();
  }
}
