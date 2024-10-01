package tv.tracker.api.infrastructure.repositories.exposed.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object ActorTable : Table("actors") {
    val id = long("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val name = varchar("name", 255)
    val birthday = date("birthday").nullable().default(null)
    val deathday = date("deathday").nullable().default(null)
    val description = text("description").nullable().default(null)
    val creationDate = timestamp("creation_date")
    val updatedAt = timestamp("last_update_date").nullable().default(null)
}
