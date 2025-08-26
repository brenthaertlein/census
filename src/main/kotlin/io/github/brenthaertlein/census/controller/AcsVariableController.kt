package io.github.brenthaertlein.census.controller

import io.github.brenthaertlein.census.acs.AcsVariable
import io.github.brenthaertlein.census.acs.AcsVariableService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/acs/variable"])
class AcsVariableController(private val americanCommunitySurveyService: AcsVariableService) {

    @GetMapping
    fun getAcsVariables(
        @RequestParam(required = false) name: Array<String> = emptyArray(),
        @RequestParam("search_text", required = false) searchText: Array<String> = emptyArray(),
        pageable: Pageable,
    ): Page<AcsVariable> = when {
        name.isNotEmpty() && searchText.isNotEmpty() -> throw IllegalArgumentException("Cannot specify both 'name' and 'search_text' parameters")
        searchText.isNotEmpty() -> americanCommunitySurveyService.getSurveyVariablesByTerms(searchText, pageable)
        name.isNotEmpty() -> americanCommunitySurveyService.getSurveyVariablesByName(name.toSet(), pageable)

        else -> americanCommunitySurveyService.getSurveyVariables(pageable)
    }
}
