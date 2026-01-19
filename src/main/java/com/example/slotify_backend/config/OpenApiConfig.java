package com.example.slotify_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Slotify backend")
                        .version("1.0")
                        .description("Backend API Slotify application")
                        .contact(new Contact()
                                .name("Filip Jarocki")
                                .email("filip.jarocki@wp.pl")));
    }
}
