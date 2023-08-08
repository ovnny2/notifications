package com.ovnny.notifications.config.dependecies

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver.jwt")
@RefreshScope
class OAuth2ResourceServer {
    var jwt: Jwt? = null

    class Jwt { var issuerUri: String? = null }
}