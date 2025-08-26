package io.github.brenthaertlein.census.actuator.health

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.DescribeClusterOptions
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component

@Component
class KafkaHealthIndicator(
    private val adminClient: AdminClient
) : HealthIndicator {
    override fun health(): Health {
        return try {
            val options = DescribeClusterOptions().timeoutMs(2000)
            val cluster = adminClient.describeCluster(options)
            val nodes = cluster.nodes().get()
            if (nodes.isNotEmpty()) {
                Health.up().withDetail("kafka", "Available").withDetail("nodes", nodes.size).build()
            } else {
                Health.down().withDetail("kafka", "No nodes available").build()
            }
        } catch (ex: Exception) {
            Health.down(ex).withDetail("kafka", "Unavailable").build()
        }
    }
}