package fr.rtransat.tvtracker.api.controllers

import fr.rtransat.tvtracker.api.controllers.responses.ActorResponse
import fr.rtransat.tvtracker.api.controllers.responses.toResponse
import fr.rtransat.tvtracker.api.dto.SortType
import fr.rtransat.tvtracker.api.requests.CreateActorRequest
import fr.rtransat.tvtracker.api.requests.PutActorRequest
import fr.rtransat.tvtracker.domain.actor.command.CreateActor
import fr.rtransat.tvtracker.domain.actor.command.DeleteActorById
import fr.rtransat.tvtracker.domain.actor.command.PutActorById
import fr.rtransat.tvtracker.domain.actor.query.GetActorById
import fr.rtransat.tvtracker.domain.actor.query.ListActor
import jakarta.validation.Valid
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/v1/actors")
class ActorController(
    private val applicationContext: ApplicationContext
) {
    @GetMapping
    fun list(@RequestParam(value = "sort", required = false) sort: SortType?): ResponseEntity<List<ActorResponse>> {
        val response = applicationContext.getBean<ListActor>()
            .handle(ListActor.Request(sort ?: SortType.ASC))

        return ResponseEntity(response.actorList.map { it.toResponse() }, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<ActorResponse> {
        val response = applicationContext.getBean<GetActorById>()
            .handle(GetActorById.Request(id))

        return ResponseEntity(response.actor.toResponse(), HttpStatus.OK)
    }

    @PostMapping
    fun post(@Valid @RequestBody body: CreateActorRequest): ResponseEntity<Any> {
        val id = applicationContext.getBean<CreateActor>()
            .handle(CreateActor.Command(body.name))

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/$id").build().toUri())
            .build()
    }

    @PutMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun put(@PathVariable id: Long, @Valid @RequestBody body: PutActorRequest) {
        applicationContext.getBean<PutActorById>()
            .handle(PutActorById.Command(id, body.name))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        applicationContext.getBean<DeleteActorById>()
            .handle(DeleteActorById.Command(id))
    }
}
