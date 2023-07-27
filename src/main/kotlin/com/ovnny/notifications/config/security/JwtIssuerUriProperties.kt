package com.ovnny.notifications.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
@Configuration
class JwtIssuerUriProperties {

    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    var issuerUri: String? = ""
}