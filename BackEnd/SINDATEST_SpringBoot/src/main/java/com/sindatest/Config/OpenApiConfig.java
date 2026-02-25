package com.sindatest.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API Système SINDA - Déclarations")
                                 .description("Documentation des APIs pour la gestion des déclarations DECENT")
                                 .version("1.0"));
    }
}