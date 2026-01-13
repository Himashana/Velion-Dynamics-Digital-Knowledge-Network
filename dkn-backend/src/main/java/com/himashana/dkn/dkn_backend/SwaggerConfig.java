package com.himashana.dkn.dkn_backend;

import org.springdoc.webmvc.core.SpringDocWebMvcConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@Import(SpringDocWebMvcConfiguration.class)
public class SwaggerConfig {

	@Autowired
	AppProperties appProperties;
	
    @Bean
     public OpenAPI springOpenAPI() {
        return new OpenAPI()
			.addSecurityItem(new SecurityRequirement().
				addList("Bearer Authentication"))
				.components(new Components().addSecuritySchemes
				("Bearer Authentication", new SecurityScheme().type(SecurityScheme.Type.HTTP)
											.bearerFormat("JWT")
											.scheme("bearer")))
            .info(
				new Info()
						.title(appProperties.getAppName())
						.description("API Documentation")
						.version("API v1")
			);
    }  
}