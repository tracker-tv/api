package tv.tracker.api.application.views

import kotlinx.serialization.Serializable
import tv.tracker.api.domain.actor.Actor

@Serializable
data class ActorView(
    val id: Long,
    val name: String,
) {
    companion object {
        fun from(actor: Actor) =
            ActorView(
                id = actor.id,
                name = actor.name,
            )
    }
}
