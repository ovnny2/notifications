package com.ovnny.notifications.config

import com.ovnny.notifications.config.keycloak.KeycloakClientProvider
import com.ovnny.notifications.config.keycloak.ResourceServer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy


@Configuration
@EnableWebSecurity
class SecurityConfig(
    val keycloakClientProvider: KeycloakClientProvider,
    val resourceServer: ResourceServer
) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeRequests()
            .antMatchers("/v1**")
            .hasRole("USER")
            .anyRequest()
            .authenticated()
        http.oauth2ResourceServer { obj: OAuth2ResourceServerConfigurer<HttpSecurity?> -> obj.jwt() }
        return http.build()
    }

    @Bean
    protected fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy {
        return RegisterSessionAuthenticationStrategy(SessionRegistryImpl())
    }

    @Bean
    protected fun jwtDecoder(): JwtDecoder {
        return JwtDecoders.fromIssuerLocation(keycloakClientProvider.issuerUri)
    }
}