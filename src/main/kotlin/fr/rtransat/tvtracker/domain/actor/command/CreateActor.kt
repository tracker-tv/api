package fr.rtransat.tvtracker.domain.actor.command

import fr.rtransat.tvtracker.domain.actor.Actor
import fr.rtransat.tvtracker.domain.actor.ActorRepository
import kotlinx.datetime.Clock

class CreateActor(
    private val actorRepository: ActorRepository,
    private val clock: Clock
) {
    fun handle(command: Command): Long = actorRepository.save(
        Actor(
            command.name,
            clock.now()
        )
    ).id!!

    data class Command(
        val name: String
    )
}
