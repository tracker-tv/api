package tv.tracker.api.domain.actor

import kotlinx.datetime.Instant

data class Actor(
    val id: Long,
    val name: String,
    val creationDate: Instant,
    val lastUpdateDate: Instant?,
)
