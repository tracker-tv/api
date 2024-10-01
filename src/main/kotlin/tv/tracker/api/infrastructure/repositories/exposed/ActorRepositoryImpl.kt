package tv.tracker.api.infrastructure.repositories.exposed

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import tv.tracker.api.domain.actor.Actor
import tv.tracker.api.domain.actor.ActorRepository
import tv.tracker.api.infrastructure.repositories.exposed.tables.ActorTable

@Repository
@Transactional(readOnly = true)
class ActorRepositoryImpl : ActorRepository {
    override fun findAll() = ActorTable.selectAll().map { fromRow(it) }

    private fun fromRow(r: ResultRow) =
        Actor(
            id = r[ActorTable.id],
            name = r[ActorTable.name],
            birthday = r[ActorTable.birthday],
            deathday = r[ActorTable.deathday],
            description = r[ActorTable.description],
            creationDate = r[ActorTable.creationDate],
            lastUpdateDate = r[ActorTable.updatedAt],
        )
}
