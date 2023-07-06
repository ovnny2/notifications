package com.ovnny.notifications.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean


@Configuration
class MessageConfig {

    @Bean
    fun messageSource(): ResourceBundleMessageSource? {
        val source = ResourceBundleMessageSource()
        source.setBasenames("messages/label")
        source.setUseCodeAsDefaultMessage(true)
        return source
    }

    @Bean
    fun getValidator(): LocalValidatorFactoryBean? {
        val bean = LocalValidatorFactoryBean()
        bean.setValidationMessageSource(messageSource()!!)
        return bean
    }
}