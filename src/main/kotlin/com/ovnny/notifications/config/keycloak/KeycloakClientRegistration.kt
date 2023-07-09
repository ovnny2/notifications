package com.ovnny.notifications.config.keycloak

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component


@Component
@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.keycloak")
class KeycloakClientRegistration {
    lateinit var clientId: String
    lateinit var clientName: String
    lateinit var redirectUri: String
    lateinit var authorizationGrantType: String
    lateinit var clientAuthenticationMethod: String
    lateinit var scope: String
}