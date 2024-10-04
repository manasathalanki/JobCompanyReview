package com.example.youtube_learning.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	private static final String SECURITY_SCHEME_NAME = "bearerAuth";

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Company, Job, and Review API")
						.description("API for managing companies, jobs, and reviews").version("v1.0")
						.contact(new Contact().name("Support Team").email("support@example.com"))
						.license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation().description("Company API Documentation")
						.url("https://example.com/docs"))
				.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME)) // Add security requirement
				.components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME,
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
		
	}

	@Bean
	public GroupedOpenApi companyApi() {
		return GroupedOpenApi.builder().group("companies").pathsToMatch("/companies/**").build();
	}

	@Bean
	public GroupedOpenApi loginApi() {
		return GroupedOpenApi.builder().group("login").pathsToMatch("/login/**").build();
	}

	@Bean
	public GroupedOpenApi jobApi() {
		return GroupedOpenApi.builder().group("jobs").pathsToMatch("/jobs/**").build();
	}

	@Bean
	public GroupedOpenApi reviewApi() {
		return GroupedOpenApi.builder().group("reviews").pathsToMatch("/companies/{companyId}/reviews/**").build();
	}
}
