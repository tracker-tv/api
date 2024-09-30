package tv.tracker.api.application.controllers

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import tv.tracker.api.application.views.ActorView
import tv.tracker.api.domain.actor.ActorRepository

@RestController
class ActorController(
    private val actorRepository: ActorRepository,
) {
    @GetMapping("/actors", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getActors(): List<ActorView> = actorRepository.findAll().map { ActorView.from(it) }
}
