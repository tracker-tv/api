package fr.rtransat.tvtracker.api.infrastructure.exposed.repositories

import fr.rtransat.tvtracker.api.dto.SortType
import fr.rtransat.tvtracker.api.infrastructure.exposed.tables.ActorTable
import fr.rtransat.tvtracker.domain.actor.Actor
import fr.rtransat.tvtracker.domain.actor.ActorRepository
import fr.rtransat.tvtracker.domain.actor.query.ListActor
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class ActorRepositoryImpl : AbstractRepository(), ActorRepository {
    @Transactional("transaction-manager-ro")
    override fun findAll(sortCriteria: ListActor.SortCriteria): List<Actor> {
        val query = ActorTable.selectAll()

        query.orderBy(
            ActorTable.creationDate to when (sortCriteria.sortType) {
                SortType.ASC -> SortOrder.ASC
                SortType.DESC -> SortOrder.DESC
            }
        )

        return query.map { fromRow(it) }
    }

    @Transactional("transaction-manager-ro")
    override fun findById(id: Long): Actor? =
        ActorTable.select { ActorTable.id eq id }.firstOrNull()?.let { fromRow(it) }


    @Transactional("transaction-manager-ro")
    override fun findByName(name: String): Actor? =
        ActorTable.select { ActorTable.name eq name }.firstOrNull()?.let { fromRow(it) }


    @Transactional("transaction-manager-rw")
    override fun save(actor: Actor): Actor = ActorTable.insert {
        it[name] = actor.name
        it[creationDate] = actor.creationDate
    }.resultedValues!!.first().let { fromRow(it) }

    @Transactional("transaction-manager-rw")
    override fun update(actor: Actor) {
        ActorTable.update({ ActorTable.id eq actor.id!! }) {
            it[name] = actor.name
            it[lastUpdateDate] = actor.lastUpdateDate
        }
    }

    @Transactional("transaction-manager-rw")
    override fun delete(id: Long) {
        ActorTable.deleteWhere { ActorTable.id eq id }
    }

    override fun fromRow(r: ResultRow) = Actor(
        id = r[ActorTable.id],
        name = r[ActorTable.name],
        creationDate = r[ActorTable.creationDate],
        lastUpdateDate = r[ActorTable.lastUpdateDate]
    )
}
