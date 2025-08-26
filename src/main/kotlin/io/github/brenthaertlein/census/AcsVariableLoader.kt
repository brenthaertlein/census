package io.github.brenthaertlein.census

import io.github.brenthaertlein.census.acs.AcsVariableRepository
import io.github.brenthaertlein.census.client.acs.AmericanCommunitySurveyFeignClient
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class AcsVariableLoader(
    private val acsVariableRepository: AcsVariableRepository,
    private val americanCommunitySurveyFeignClient: AmericanCommunitySurveyFeignClient,
    private val jdbcTemplate: JdbcTemplate,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        when (acsVariableRepository.count()) {
            0L -> {
                val variables = americanCommunitySurveyFeignClient.getSurveyVariables("2023")
                val headers = variables.first()
                val data = variables.drop(4)
                // Prepare batch parameters
                val batchParams = data.map { row ->
                    val label = row[1].orEmpty()
                    val concept = row[2].orEmpty()
                    arrayOf(
                        row[0], // name
                        label,
                        concept,
                        buildTsvector(label, concept), // document_tsv
                        buildEmbedding(label, concept)  // embedding, optional
                    )
                }

                jdbcTemplate.batchUpdate(
                    """
                    INSERT INTO acs_variable (name, label, concept, document_tsv, embedding)
                    VALUES (?, ?, ?, to_tsvector(?), ?::vector)
                    """.trimIndent(),
                    batchParams
                )
            }
        }
    }

    private fun buildTsvector(label: String, concept: String): String =
        listOf(label.replace("!!", " "), concept)
            .filter { it.isNotBlank() }
            .joinToString(" ")

    private fun buildEmbedding(label: String, concept: String): String {
        // placeholder 1536-dimensional vector as string for pgvector
        return (0 until 1536).joinToString(",", "[", "]") { kotlin.random.Random.nextFloat().toString() }
    }
}