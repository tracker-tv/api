package tv.tracker.api.application.views

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import tv.tracker.api.domain.actor.Actor

@Serializable
data class ActorView(
    val id: Long,
    val name: String,
    val birthday: LocalDate?,
    val deathday: LocalDate?,
    val description: String?,
) {
    companion object {
        fun from(actor: Actor) =
            ActorView(
                id = actor.id,
                name = actor.name,
                birthday = actor.birthday,
                deathday = actor.deathday,
                description = actor.description,
            )
    }
}
