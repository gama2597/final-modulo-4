package com.tecsup.app.micro.order.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI orderServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Order Service API - Clean Architecture")
                        .description("Microservicio encargado de gestionar las órdenes y compras de los usuarios.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Gabriel Macavilca")
                                .email("gmacavilca2597@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}