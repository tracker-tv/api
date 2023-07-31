package fr.rtransat.tvtracker.api.infrastructure.exposed.repositories

import fr.rtransat.tvtracker.domain.shared.Entity
import org.jetbrains.exposed.sql.ResultRow

abstract class AbstractRepository {
    protected abstract fun fromRow(r: ResultRow): Entity
}
