package com.moodsinger.ccrt_clinic.shared;

import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moodsinger.ccrt_clinic.AppProperties;
import com.moodsinger.ccrt_clinic.io.enums.Role;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;

@Component
public class Utils {

  @Autowired
  private AppProperties appProperties;

  private Random random = new SecureRandom();
  private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  public String generateUserId(int length) {
    return generateRandomString(length);
  }

  private String generateRandomString(int length) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      stringBuilder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
    }
    return stringBuilder.toString();
  }

  public boolean isNonNullAndNonEmpty(String val) {
    return val != null && !val.isEmpty();
  }

  public boolean validateEmail(String email) {
    if (!isNonNullAndNonEmpty(email))
      return false;
    String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

  public boolean validatePassword(String password) {
    if (!isNonNullAndNonEmpty(password))
      return false;
    return password.length() >= Integer.parseInt(appProperties.getProperty("minimum-password-length"));
  }

  public boolean validateUserType(String userType) {
    if (!isNonNullAndNonEmpty(userType))
      return false;
    return userType.equals(Role.USER.name()) || userType.equals(Role.DOCTOR.name());
  }

  public boolean validateVerificationStatus(String verificationStatus) {
    if (!isNonNullAndNonEmpty(verificationStatus))
      return false;
    return verificationStatus.equals(VerificationStatus.ACCEPTED.name())
        || verificationStatus.equals(VerificationStatus.REJECTED.name())
        || verificationStatus.equals(VerificationStatus.PENDING.name());
  }
}
