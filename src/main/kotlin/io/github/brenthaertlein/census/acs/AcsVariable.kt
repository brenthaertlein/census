package io.github.brenthaertlein.census.acs

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class AcsVariable(
    @Id
    val name: String,
    val label: String,
    val concept: String,
)
