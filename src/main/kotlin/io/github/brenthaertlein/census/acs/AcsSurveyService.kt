package io.github.brenthaertlein.census.acs

import io.github.brenthaertlein.census.client.acs.AmericanCommunitySurveyFeignClient
import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Component

@Component
class AcsSurveyService(
    private val americanCommunitySurveyFeignClient: AmericanCommunitySurveyFeignClient,
    private val chatClient: ChatClient,
) {

    fun getSurveyData(variables: List<AcsVariable>): List<Map<String, String>> {
        // Example response:
        // [["B01003_001E","state","county","county subdivision"],
        //["8992","12","031","90110"],
        //["58479","12","031","91640"],
        //["499813","12","031","91642"],
        //["97147","12","031","91644"],
        //["342758","12","031","91646"]]
        val surveyData = americanCommunitySurveyFeignClient.getSurveyData(
            year = "2023",
            variables = variables.joinToString(",") { it.name },
            type = "county subdivision:*",
            target = "state:12+county:031"
        )
        val headers = surveyData.first()
        val data = surveyData.drop(1)

        val output = data.map { row ->
            headers.zip(row).toMap()
                .apply {
                    val geoId = listOf(get("state"), get("county"), get("county subdivision")).joinToString()
                    plus("GEO_ID" to geoId)
                }
        }
            .apply {
                val summary = chatClient.prompt()
                    .system("Summarize the following census data in a concise manner.")
                    .user(
                        """
                        Here is the census data:
                        ${joinToString("\n") { map -> map.entries.joinToString("\n") { "${it.key}: ${it.value}" } }}
                        """.trimIndent()
                    )
                    .call()
                    .content()
                plus("summary" to summary)
            }

        return output
    }
}