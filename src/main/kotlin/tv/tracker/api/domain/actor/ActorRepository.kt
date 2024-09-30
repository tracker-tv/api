package tv.tracker.api.domain.actor

interface ActorRepository {
    fun findAll(): List<Actor>
}
