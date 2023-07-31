package fr.rtransat.tvtracker.domain.actor

import fr.rtransat.tvtracker.domain.shared.Entity
import kotlinx.datetime.Instant

data class Actor(
    val id: Long? = null,
    var name: String,
    val creationDate: Instant,
    var lastUpdateDate: Instant?
) : Entity {
    constructor(name: String, creationDate: Instant) : this(null, name, creationDate, null)
}
