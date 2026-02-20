package com.tecsup.app.micro.product.infrastructure.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@Slf4j
public class BeanConfig {

    @Value("${app.security.system-account.username}")
    private String systemUsername;

    @Value("${app.security.system-account.password}")
    private String systemPassword;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        log.info("Configurando RestTemplate con credenciales de sistema para usuario: {}", systemUsername);
        return builder
                // ¡AQUÍ ESTÁ LA MAGIA! Le inyectamos el usuario y contraseña:
                .basicAuthentication(systemUsername, systemPassword)
                // Mantenemos tus excelentes configuraciones de timeout:
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(5))
                .build();
    }
}