package io.github.brenthaertlein.census.actuator.health

import io.github.brenthaertlein.census.client.ollama.OllamaFeignClient
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component

@Component
class OllamaHealthIndicator(private val ollamaFeignClient: OllamaFeignClient) : HealthIndicator {

    override fun health(): Health {
        return try {
            val result = ollamaFeignClient.getModels()

            if (result.isNotEmpty()) {
                Health.up().withDetail("models", result).build()
            } else {
                Health.down().withDetail("error", "No models returned").build()
            }
        } catch (e: Exception) {
            Health.down(e).build()
        }
    }
}
