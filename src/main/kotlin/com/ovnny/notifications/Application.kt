package com.ovnny.notifications

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
@EnableConfigurationProperties
@SpringBootApplication
class Application

    fun main(args: Array<String>) {
        runApplication<Application>(*args)
    }