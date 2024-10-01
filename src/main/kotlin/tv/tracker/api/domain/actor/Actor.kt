package tv.tracker.api.domain.actor

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

data class Actor(
    val id: Long,
    val name: String,
    val birthday: LocalDate?,
    val deathday: LocalDate?,
    val description: String?,
    val creationDate: Instant,
    val lastUpdateDate: Instant?,
)
