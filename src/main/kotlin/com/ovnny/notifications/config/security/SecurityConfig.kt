package com.ovnny.notifications.config.security

import com.ovnny.notifications.config.dependecies.OAuth2ResourceServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Autowired private val resourceServer: OAuth2ResourceServer
) {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http.authorizeHttpRequests { request -> request.anyRequest().authenticated() }

        http.oauth2ResourceServer { obj: OAuth2ResourceServerConfigurer<HttpSecurity?> -> obj.jwt() }

        return http.build()
    }

    @Bean
    protected fun jwtDecoder(): JwtDecoder {
        val issuerUri: String = resourceServer.jwt?.issuerUri ?: "http://172.21.0.2:8080/realms/craftzn"

        return JwtDecoders.fromIssuerLocation(issuerUri)
    }
}