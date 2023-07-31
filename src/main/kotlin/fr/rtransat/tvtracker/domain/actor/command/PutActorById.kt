package fr.rtransat.tvtracker.domain.actor.command

import fr.rtransat.tvtracker.domain.actor.ActorRepository
import fr.rtransat.tvtracker.domain.actor.exceptions.ActorNotFoundException
import kotlinx.datetime.Clock

class PutActorById(private val actorRepository: ActorRepository, private val clock: Clock) {
    fun handle(command: Command) {
        val actor = actorRepository.findById(command.id) ?: throw ActorNotFoundException(command.id)
        actor.name = command.name
        actor.lastUpdateDate = clock.now()

        actorRepository.update(actor)
    }

    data class Command(
        val id: Long,
        val name: String
    )

}
