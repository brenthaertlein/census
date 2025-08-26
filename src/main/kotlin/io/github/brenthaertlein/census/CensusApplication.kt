package io.github.brenthaertlein.census

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.web.config.EnableSpringDataWebSupport

@EnableFeignClients
@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
class CensusApplication

fun main(args: Array<String>) {
    runApplication<CensusApplication>(*args)
}
