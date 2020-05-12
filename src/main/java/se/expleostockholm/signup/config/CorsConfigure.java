package se.expleostockholm.signup.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfigure implements WebMvcConfigurer {

    /**
     * Enabling CORS for specific endpoints.
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://dashboard.heroku.com/apps/signup5-ui-dev", "https://signup5-ui-stage.herokuapp.com/")
                .allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE");
    }
}