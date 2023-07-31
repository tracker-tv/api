package fr.rtransat.tvtracker.domain.integrations

import fr.rtransat.tvtracker.BaseIntegrationTest
import fr.rtransat.tvtracker.domain.actor.Actor
import kotlinx.datetime.Instant
import org.springframework.core.io.ResourceLoader
import javax.sql.DataSource

abstract class BaseDomainIntegrationTest(
    resourceLoader: ResourceLoader,
    datasource: DataSource,
) : BaseIntegrationTest(resourceLoader, datasource) {
    val actor1 by lazy { getBryanCranston() }

    fun getBryanCranston() =
        Actor(
            1,
            "Bryan Cranston",
            Instant.parse("2020-11-20T14:00:00Z"),
            null
        )
}
