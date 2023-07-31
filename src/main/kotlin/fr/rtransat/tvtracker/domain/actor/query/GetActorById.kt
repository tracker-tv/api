package fr.rtransat.tvtracker.domain.actor.query

import fr.rtransat.tvtracker.domain.actor.Actor
import fr.rtransat.tvtracker.domain.actor.ActorRepository
import fr.rtransat.tvtracker.domain.actor.exceptions.ActorNotFoundException

class GetActorById(
    private val actorRepository: ActorRepository
) {
    fun handle(request: Request): Response =
        actorRepository.findById(request.id)?.let { actor -> Response(actor) }
            ?: throw ActorNotFoundException(request.id)

    data class Request(
        val id: Long
    )

    data class Response(
        val actor: Actor
    )
}
