package com.tcet.companyassistant.company_assistant.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI companyAssistantOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Company Assistant API")
                        .version("v1")
                        .description("REST API for company workplace assistance")
                        .contact(new Contact()
                                .name("Company Assistant Team"))
                        .license(new License()
                                .name("Internal Use")));
    }
}
