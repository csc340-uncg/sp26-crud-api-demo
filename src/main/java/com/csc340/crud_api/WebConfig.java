package com.csc340.crud_api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Value("${upload.dir}")
  private String uploadDir;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 1. Handle Profile Pictures from external volume
    registry.addResourceHandler("/profile-pictures/**")
        .addResourceLocations("file:" + uploadDir + "/profile-pictures/");

    // 2. Handle standard static files (CSS, JS) from the JAR
    registry.addResourceHandler("/**")
        .addResourceLocations("classpath:/static/");
  }

}
