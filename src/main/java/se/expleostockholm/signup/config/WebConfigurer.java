package se.expleostockholm.signup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {


  @Bean
  public WebConfigurer corsConfiguration() {
    return new WebConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/graphql").allowedOrigins("http://localhost:3000");
      }
    };
  }
}
