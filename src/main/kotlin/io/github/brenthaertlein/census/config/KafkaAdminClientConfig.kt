package io.github.brenthaertlein.census.config

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.AdminClientConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.ssl.DefaultSslBundleRegistry

@Configuration
class KafkaAdminClientConfig(private val kafkaProperties: KafkaProperties) {
    @Bean
    fun kafkaAdminClient(): AdminClient {
        val config = HashMap<String, Any>()
        config.putAll(kafkaProperties.buildAdminProperties(DefaultSslBundleRegistry()))
        return AdminClient.create(config)
    }
}

