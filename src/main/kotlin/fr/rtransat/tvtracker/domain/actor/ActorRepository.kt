package fr.rtransat.tvtracker.domain.actor

import fr.rtransat.tvtracker.domain.actor.query.ListActor

interface ActorRepository {
    fun findAll(sortCriteria: ListActor.SortCriteria): List<Actor>

    fun findById(id: Long): Actor?

    fun findByName(name: String): Actor?

    fun save(actor: Actor): Actor

    fun update(actor: Actor)

    fun delete(id: Long)
}
