package io.github.brenthaertlein.census.client.acs

import com.fasterxml.jackson.annotation.JsonProperty

data class AmericanCommunitySurveyQueryMap(
    @JsonProperty("for")
    val type: String,
    @JsonProperty("in")
    val target: String,
)
