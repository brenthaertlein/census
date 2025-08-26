package io.github.brenthaertlein.census.acs

import io.github.brenthaertlein.census.client.acs.AmericanCommunitySurveyFeignClient
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class AcsVariableService(
    private val acsVariableRepository: AcsVariableRepository,
) {

    fun getSurveyVariables(pageable: Pageable): Page<AcsVariable> =
        acsVariableRepository.findAll(pageable)

    fun getSurveyVariablesByName(names: Set<String>, pageable: Pageable): Page<AcsVariable> =
        acsVariableRepository.findByNameIn(names, pageable)

    fun getSurveyVariablesByTerms(terms: Array<String>, pageable: Pageable): Page<AcsVariable> {
        val allTerms = terms.joinToString(" | ") { "'$it'" }
        return when (val count = acsVariableRepository.countByText(allTerms)) {
            0L -> PageImpl(emptyList(), pageable, 0)
            else -> acsVariableRepository.searchByTerms(
                allTerms = allTerms,
                terms = terms,
                limit = pageable.pageSize,
                offset = pageable.offset
            )
                .let { PageImpl(it, pageable, count) }
        }
    }
}