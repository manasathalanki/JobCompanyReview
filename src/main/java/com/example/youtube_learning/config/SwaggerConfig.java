package com.example.youtube_learning.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("Company, Job, and Review API")
                .description("API for managing companies, jobs, and reviews")
                .version("v1.0")
                .contact(new Contact().name("Support Team").email("support@example.com"))
                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
            .externalDocs(new ExternalDocumentation()
                .description("Company API Documentation")
                .url("https://example.com/docs"));
    }

    @Bean
    public GroupedOpenApi companyApi() {
        return GroupedOpenApi.builder()
            .group("companies")
            .pathsToMatch("/companies/**")
            .build();
    }
    
    @Bean
    public GroupedOpenApi loginApi() {
        return GroupedOpenApi.builder()
            .group("login")
            .pathsToMatch("/login/**")
            .build();
    }

    @Bean
    public GroupedOpenApi jobApi() {
        return GroupedOpenApi.builder()
            .group("jobs")
            .pathsToMatch("/jobs/**")
            .build();
    }

    @Bean
    public GroupedOpenApi reviewApi() {
        return GroupedOpenApi.builder()
            .group("reviews")
            .pathsToMatch("/companies/{companyId}/reviews/**")
            .build();
    }
}
