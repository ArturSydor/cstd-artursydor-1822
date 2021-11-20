package com.lpnu.ecoplatformserver.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.lpnu.ecoplatformserver.security.JwtAuthenticationFilter.AUTHORIZATION_HEADER;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Components section defines Security Scheme "mySecretHeader"
                .components(new Components()
                        .addSecuritySchemes(AUTHORIZATION_HEADER, new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(AUTHORIZATION_HEADER)))
                // AddSecurityItem section applies created scheme globally
                .addSecurityItem(new SecurityRequirement().addList(AUTHORIZATION_HEADER));
    }
}
