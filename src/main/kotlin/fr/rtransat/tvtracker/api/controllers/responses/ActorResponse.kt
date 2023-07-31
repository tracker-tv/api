package fr.rtransat.tvtracker.api.controllers.responses

import fr.rtransat.tvtracker.domain.actor.Actor
import kotlinx.serialization.Serializable

@Serializable
data class ActorResponse(
    val id: Long,
    val name: String
)

fun Actor.toResponse() = ActorResponse(
    id!!,
    name
)
