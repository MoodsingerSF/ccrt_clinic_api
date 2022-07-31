package com.moodsinger.ccrt_clinic.security;

import com.moodsinger.ccrt_clinic.AppProperties;
import com.moodsinger.ccrt_clinic.SpringApplicationContext;

public class SecurityConstants {
  public static final long EXPIRATION_TIME = 864000000;
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String SIGN_UP_URL = "/users";
  public static final String LOG_IN_URL = "/login";

  public static String getSecurityToken() {
    AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("appProperties");
    return appProperties.getProperty("security-token");
  }
}
