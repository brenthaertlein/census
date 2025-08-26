package io.github.brenthaertlein.census.client.acs

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "americanCommunitySurvey",
    url = "https://api.census.gov/data",
    configuration = [AmericanCommunitySurveyFeignConfiguration::class]
)
interface AmericanCommunitySurveyFeignClient {

    @GetMapping("/{year}/acs/acs5")
    fun getSurveyData(
        @PathVariable year: String,
        @RequestParam("get") variables: String,
        @RequestParam("for") type: String,
        @RequestParam("in") target: String,
    ): List<List<String>>

    @GetMapping("/{year}/acs/acs5")
    fun getSurveyData(
        @PathVariable year: String,
        @RequestParam("get") variables: String,
        @SpringQueryMap data: AmericanCommunitySurveyQueryMap,
    ): List<List<String>>

    @GetMapping("/{year}/acs/acs5/variables")
    fun getSurveyVariables(
        @PathVariable year: String,
    ): List<List<String>>
}