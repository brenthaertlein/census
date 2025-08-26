package io.github.brenthaertlein.census.controller

import io.github.brenthaertlein.census.acs.AcsSurveyService
import io.github.brenthaertlein.census.acs.AcsVariableService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/acs/survey"])
class AcsSurveyController(
    private val acsVariableService: AcsVariableService,
    private val acsSurveyService: AcsSurveyService
) {

    @GetMapping
    fun getAcsSurveys(@RequestParam variables: Set<String>) =
        acsVariableService.getSurveyVariablesByName(variables, Pageable.ofSize(Int.MAX_VALUE))
            .let { acsSurveyService.getSurveyData(it.content) }
}