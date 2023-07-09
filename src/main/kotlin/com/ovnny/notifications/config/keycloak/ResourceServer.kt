package com.ovnny.notifications.config.keycloak

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver.jwt")
class ResourceServer {
    lateinit var jwt: JwtProperties

    class JwtProperties{
        lateinit var issuerUri: String
    }
}