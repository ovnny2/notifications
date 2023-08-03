package com.ovnny.notifications.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http.oauth2ResourceServer {
            obj: OAuth2ResourceServerConfigurer<HttpSecurity?> -> obj.jwt()
        }

        http.authorizeHttpRequests { request ->
            request.anyRequest().authenticated()
        }

        return http.build()
    }

    @Bean
    protected fun jwtDecoder(): JwtDecoder {
        return JwtDecoders.fromIssuerLocation("http://172.21.0.2:8080/realms/craftzn")
    }
}