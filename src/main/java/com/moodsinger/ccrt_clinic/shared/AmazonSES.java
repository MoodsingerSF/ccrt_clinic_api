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

@Component
public class AmazonSES {
  private final String FROM = "rafi1017623150@gmail.com";
  private final String SUBJECT = "[CCRT Clinic] Email Verification Code";

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

}
