package io.github.brenthaertlein.census.client.acs

import feign.RequestInterceptor
import org.springframework.context.annotation.Bean

class AmericanCommunitySurveyFeignConfiguration {

    @Bean
    fun requestInterceptor() = RequestInterceptor {
        it.uri(
            it.request().url()
                .replace("%2A", "*")
                .replace("%2B", "+")
                .replace("%2C", ",")
                .replace("%3A", ":")
        )
    }
}