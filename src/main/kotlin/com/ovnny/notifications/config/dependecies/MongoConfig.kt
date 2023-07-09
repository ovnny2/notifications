package com.ovnny.notifications.config.dependecies

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import java.util.*


@Configuration
@EnableMongoRepositories(basePackages = ["com.ovnny.notifications.repository"])
class MongoConfig: AbstractMongoClientConfiguration() {

    @Value("\${spring.data.mongodb.uri}")
    lateinit var uri: String

    @Value("\${spring.data.mongodb.database}")
    lateinit var database: String

    @Value("\${spring.data.mongodb.host}")
    lateinit var host: String

    @Value("\${spring.data.mongodb.username}")
    lateinit var username: String

    @Value("\${spring.data.mongodb.password}")
    lateinit var password: String


    @Bean
    override fun mongoClient(): MongoClient {
        return MongoClients.create(uri)
    }

    @Bean
    override fun getDatabaseName(): String {
        return database
    }

    @Bean
    override fun getMappingBasePackages(): MutableCollection<String> {
        return arrayListOf("com.ovnny.notifications.model")
    }
}