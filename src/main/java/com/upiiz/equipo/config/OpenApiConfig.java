package com.upiiz.equipo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Equipos Deportivos")
                        .description("API REST para la gestión de equipos deportivos, ligas, competencias, jugadores y entrenadores")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Nombre del Desarrollador")
                                .email("correo@ejemplo.com")
                                .url("https://tu-sitio-web.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
