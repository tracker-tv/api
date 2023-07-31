package fr.rtransat.tvtracker.domain.actor.query

import fr.rtransat.tvtracker.api.dto.SortType
import fr.rtransat.tvtracker.domain.actor.Actor
import fr.rtransat.tvtracker.domain.actor.ActorRepository

class ListActor(
    private val actorRepository: ActorRepository
) {
    fun handle(request: Request): Response {
        val criteria = SortCriteria(request.sortType)
        val actorList = actorRepository.findAll(criteria)

        return Response(actorList)
    }

    data class Request(
        val sortType: SortType
    )

    data class Response(
        val actorList: List<Actor>
    )


    data class SortCriteria(
        val sortType: SortType
    )
}
