package com.moodsinger.ccrt_clinic;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

  @Override
  protected void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("http:localhost:8080");
    super.addCorsMappings(registry);
  }

}
