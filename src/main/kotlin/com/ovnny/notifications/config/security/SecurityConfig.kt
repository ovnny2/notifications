package com.ovnny.notifications.config.security

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
    private val jwtProperties: JwtIssuerUriProperties
) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeRequests()
            .antMatchers("/v1/notifications**")
            .hasRole("USER")
            .anyRequest()
            .authenticated()

        http.oauth2ResourceServer {
            obj: OAuth2ResourceServerConfigurer<HttpSecurity?> -> obj.jwt()
        }

        return http.build()
    }

    @Bean
    protected fun jwtDecoder(): JwtDecoder {
        return JwtDecoders.fromIssuerLocation(jwtProperties.issuerUri)
    }
}