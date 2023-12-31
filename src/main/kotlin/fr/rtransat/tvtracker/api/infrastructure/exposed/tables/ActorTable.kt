package fr.rtransat.tvtracker.api.infrastructure.exposed.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object ActorTable : Table("actors") {
    val id = long("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val name = varchar("name", 255)
    val creationDate = timestamp("creation_date")
    val lastUpdateDate = timestamp("last_update_date").nullable().default(null)
}
