package com.example.esig.eid.security;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Configuration
public class KeycloakConfig {

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver(HttpServletRequest request) {
        return new PathBasedKeycloakConfigResolver();
    }
}
