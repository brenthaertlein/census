package io.github.brenthaertlein.census.acs

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param

interface AcsVariableRepository : CrudRepository<AcsVariable, String>, PagingAndSortingRepository<AcsVariable, String> {

    @Query("""
        SELECT name, label, concept
        FROM acs_variable, to_tsquery('english', :allTerms) q
        WHERE (name LIKE 'B%_%' OR name LIKE 'C%_%') AND document_tsv @@ q
        ORDER BY
            CASE WHEN lower(concept) = ANY(:terms) THEN 1 ELSE 0 END DESC,
            ts_rank_cd(document_tsv, q) DESC
        LIMIT :limit OFFSET :offset
    """)
    fun searchByTerms(
        @Param("allTerms") allTerms: String,
        @Param("terms") terms: Array<String>,
        @Param("limit") limit: Int,
        @Param("offset") offset: Long,
    ): List<AcsVariable>

    @Query("""
        SELECT COUNT(*) 
        FROM acs_variable
        WHERE (name LIKE 'B%_%' OR name LIKE 'C%_%') AND document_tsv @@ to_tsquery('english', :query)
    """)
    fun countByText(@Param("query") query: String): Long

    fun findByNameIn(names: Collection<String>, pageable: Pageable): Page<AcsVariable>
}