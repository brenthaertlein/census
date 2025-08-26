package io.github.brenthaertlein.census.actuator.health

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component
import org.springframework.data.redis.core.StringRedisTemplate

@Component
class RedisHealthIndicator(
    private val redisTemplate: StringRedisTemplate
) : HealthIndicator {
    override fun health(): Health {
        return try {
            val pong = redisTemplate.connectionFactory?.connection?.ping()
            if (pong == "PONG") {
                Health.up().withDetail("redis", "Available").build()
            } else {
                Health.down().withDetail("redis", "No PONG response").build()
            }
        } catch (ex: Exception) {
            Health.down(ex).withDetail("redis", "Unavailable").build()
        }
    }

}