package com.ovnny.notifications.config.keycloak

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.client.provider.keycloak")
class KeycloakClientProvider {
    lateinit var issuerUri: String
    lateinit var userNameAttribute: String
}