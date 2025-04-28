package com.vtx.jobscheduler.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import static com.vtx.jobscheduler.constants.Constants.BEARER_AUTH;
import static com.vtx.jobscheduler.constants.Constants.SCHEME_BEARER;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "jobs", version = "v1"),
        security = @SecurityRequirement(name = BEARER_AUTH)
)
@SecurityScheme(
        name = BEARER_AUTH,
        type = SecuritySchemeType.HTTP,
        scheme = SCHEME_BEARER,
        bearerFormat = "JWT"
)
public class SpringDocOpenApiConfig {
}
