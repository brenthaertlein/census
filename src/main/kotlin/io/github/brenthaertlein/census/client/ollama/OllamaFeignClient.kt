package io.github.brenthaertlein.census.client.ollama

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "ollamaClient", url = "http://localhost:11434")
interface OllamaFeignClient {

    @GetMapping("/v1/models")
    fun getModels(): Map<String, Any>
}
