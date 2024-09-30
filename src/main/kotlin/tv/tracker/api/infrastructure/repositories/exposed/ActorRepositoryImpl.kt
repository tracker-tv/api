package tv.tracker.api.infrastructure.repositories.exposed

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.springframework.transaction.annotation.Transactional
import tv.tracker.api.domain.actor.Actor
import tv.tracker.api.domain.actor.ActorRepository
import tv.tracker.api.infrastructure.repositories.exposed.tables.ActorTable

open class ActorRepositoryImpl : ActorRepository {
    @Transactional
    override fun findAll() = ActorTable.selectAll().map { fromRow(it) }

    private fun fromRow(r: ResultRow) =
        Actor(
            id = r[ActorTable.id],
            name = r[ActorTable.name],
            creationDate = r[ActorTable.creationDate],
            lastUpdateDate = r[ActorTable.updatedAt],
        )
}
