package fr.rtransat.tvtracker.domain.actor.command

import fr.rtransat.tvtracker.domain.actor.ActorRepository
import fr.rtransat.tvtracker.domain.actor.exceptions.ActorNotFoundException

class DeleteActorById(
    private val actorRepository: ActorRepository
) {
    fun handle(command: Command) {
        val actor = actorRepository.findById(command.id) ?: throw ActorNotFoundException(command.id)

        actorRepository.delete(actor.id!!)
    }

    data class Command(val id: Long)
}
