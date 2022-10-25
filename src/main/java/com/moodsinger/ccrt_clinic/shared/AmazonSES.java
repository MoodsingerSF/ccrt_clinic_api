package com.moodsinger.ccrt_clinic.shared;

import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.moodsinger.ccrt_clinic.shared.dto.AppointmentDto;
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

  public void sendVerificationEmail(String email, String code) {
    AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
        .build();

    SendEmailRequest sendEmailRequest = new SendEmailRequest()
        .withDestination(new Destination().withToAddresses(email))
        .withMessage(new Message()
            .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(getHTMLBody(code)))
                .withText(new Content().withCharset("UTF-8").withData(getTextBody(code))))
            .withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
        .withSource(FROM);
    client.sendEmail(sendEmailRequest);

  }

  public void sendRegistrationRequestAcceptanceEmail(String email, String name) {
    AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
        .build();

    SendEmailRequest sendEmailRequest = new SendEmailRequest()
        .withDestination(new Destination().withToAddresses(email))
        .withMessage(new Message()
            .withBody(new Body()
                .withHtml(new Content().withCharset("UTF-8").withData(getRegistrationRequestAcceptanceEmailBody(name)))
                .withText(new Content().withCharset("UTF-8").withData(getRegistrationRequestAcceptanceEmailText(name))))
            .withSubject(new Content().withCharset("UTF-8").withData(REGISTRATION_REQUEST_ACCEPTANCE_EMAIL_SUBJECT)))
        .withSource(FROM);
    try {
      client.sendEmail(sendEmailRequest);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void sendRegistrationRequestRejectionEmail(String email, String name) {
    AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
        .build();

    SendEmailRequest sendEmailRequest = new SendEmailRequest()
        .withDestination(new Destination().withToAddresses(email))
        .withMessage(new Message()
            .withBody(new Body()
                .withHtml(new Content().withCharset("UTF-8").withData(getRegistrationRequestRejectionEmailBody(name)))
                .withText(new Content().withCharset("UTF-8").withData(getRegistrationRequestRejectionEmailText(name))))
            .withSubject(new Content().withCharset("UTF-8").withData(REGISTRATION_REQUEST_REJECTION_EMAIL_SUBJECT)))
        .withSource(FROM);
    try {
      client.sendEmail(sendEmailRequest);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void sendPasswordResetCode(UserDto userDto, String code) {
    AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
        .build();
    String email = userDto.getEmail();
    String fullName = userDto.getFirstName() + " " + userDto.getLastName();
    SendEmailRequest sendEmailRequest = new SendEmailRequest()
        .withDestination(new Destination().withToAddresses(email))
        .withMessage(new Message()
            .withBody(new Body()
                .withHtml(new Content().withCharset("UTF-8").withData(getPrescriptionViewCodeEmailBody(fullName, code)))
                .withText(
                    new Content().withCharset("UTF-8").withData(getPrescriptionViewCodeEmailText(fullName, code))))
            .withSubject(new Content().withCharset("UTF-8").withData(PASSWORD_RESET_CODE_EMAIL_SUBJECT)))
        .withSource(FROM);
    try {
      client.sendEmail(sendEmailRequest);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void sendPrescriptionViewCode(String email, String name, String code) {
    AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
        .build();

    SendEmailRequest sendEmailRequest = new SendEmailRequest()
        .withDestination(new Destination().withToAddresses(email))
        .withMessage(new Message()
            .withBody(new Body()
                .withHtml(new Content().withCharset("UTF-8").withData(getPasswordResetCodeEmailBody(name, code)))
                .withText(new Content().withCharset("UTF-8").withData(getPasswordResetCodeEmailText(name, code))))
            .withSubject(new Content().withCharset("UTF-8").withData(PRESCRIPTION_VIEW_CODE_EMAIL_SUBJECT)))
        .withSource(FROM);
    try {
      client.sendEmail(sendEmailRequest);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void sendMeetingLink(String doctorEmail, String patientEmail, String code, String link) {
    AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
        .build();

    SendEmailRequest sendPatientEmailRequest = new SendEmailRequest()
        .withDestination(new Destination().withToAddresses(patientEmail))
        .withMessage(new Message()
            .withBody(
                new Body().withHtml(new Content().withCharset("UTF-8").withData(getMeetingHTMLBody(code, link, true)))
                    .withText(new Content().withCharset("UTF-8").withData(getMeetingTextBody(code, link, true))))
            .withSubject(new Content().withCharset("UTF-8").withData(APPOINTMENT_SUBJECT)))
        .withSource(FROM);

    SendEmailRequest sendDoctorEmailRequest = new SendEmailRequest()
        .withDestination(new Destination().withToAddresses(doctorEmail))
        .withMessage(new Message()
            .withBody(
                new Body().withHtml(new Content().withCharset("UTF-8").withData(getMeetingHTMLBody(code, link, false)))
                    .withText(new Content().withCharset("UTF-8").withData(getMeetingTextBody(code, link, false))))
            .withSubject(new Content().withCharset("UTF-8").withData(APPOINTMENT_SUBJECT)))
        .withSource(FROM);
    client.sendEmail(sendDoctorEmailRequest);
    client.sendEmail(sendPatientEmailRequest);
  }

  public void sendAppointmentCancellationEmail(AppointmentDto appointmentDto) {
    UserDto patient = appointmentDto.getPatient();
    UserDto doctor = appointmentDto.getDoctor();
    String patientEmail = patient.getEmail();
    String doctorEmail = doctor.getEmail();
    String patientName = patient.getFirstName() + " " + patient.getLastName();
    String doctorName = doctor.getFirstName() + " " + doctor.getLastName();
    AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
        .build();

    SendEmailRequest sendPatientEmailRequest = new SendEmailRequest()
        .withDestination(new Destination().withToAddresses(patientEmail))
        .withMessage(new Message()
            .withBody(
                new Body()
                    .withHtml(new Content().withCharset("UTF-8")
                        .withData(getAppointmentCancellationEmailBody(patientName, appointmentDto)))
                    .withText(new Content().withCharset("UTF-8")
                        .withData(getAppointmentCancellationEmailText(patientName, appointmentDto))))
            .withSubject(new Content().withCharset("UTF-8").withData(APPOINTMENT_SUBJECT)))
        .withSource(FROM);

    SendEmailRequest sendDoctorEmailRequest = new SendEmailRequest()
        .withDestination(new Destination().withToAddresses(doctorEmail))
        .withMessage(new Message()
            .withBody(
                new Body()
                    .withHtml(new Content().withCharset("UTF-8")
                        .withData(getAppointmentCancellationEmailBody(doctorName, appointmentDto)))
                    .withText(new Content().withCharset("UTF-8")
                        .withData(getAppointmentCancellationEmailText(doctorName, appointmentDto))))
            .withSubject(new Content().withCharset("UTF-8").withData(APPOINTMENT_SUBJECT)))
        .withSource(FROM);
    client.sendEmail(sendDoctorEmailRequest);
    client.sendEmail(sendPatientEmailRequest);
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
