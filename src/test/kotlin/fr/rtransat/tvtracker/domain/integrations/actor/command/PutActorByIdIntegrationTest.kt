package fr.rtransat.tvtracker.domain.integrations.actor.command

import fr.rtransat.tvtracker.api.infrastructure.exposed.tables.ActorTable
import fr.rtransat.tvtracker.domain.actor.ActorRepository
import fr.rtransat.tvtracker.domain.actor.command.PutActorById
import fr.rtransat.tvtracker.domain.actor.exceptions.ActorNotFoundException
import fr.rtransat.tvtracker.domain.integrations.BaseDomainIntegrationTest
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.ResourceLoader
import javax.sql.DataSource

class PutActorByIdIntegrationTest(
    @Autowired
    resourceLoader: ResourceLoader,
    @Qualifier("datasource-rw")
    datasource: DataSource,
    @Autowired
    val actorRepository: ActorRepository
) : BaseDomainIntegrationTest(resourceLoader, datasource) {
    @Test
    fun `should update an actor by id`() {
        val fixedClock = object : Clock { override fun now() = Instant.parse("2020-11-21T09:00:00Z") }
        val sut = PutActorById(actorRepository, fixedClock)

        transaction(Database.connect(datasource)) {
            val result = ActorTable.select { ActorTable.id eq 1 }.first()
            result[ActorTable.id] shouldBe 1
            result[ActorTable.name] shouldBe "Bryan Cranston"
            result[ActorTable.creationDate] shouldBe Instant.parse("2020-11-20T14:00:00Z")
            result[ActorTable.lastUpdateDate] shouldBe null
        }

        sut.handle(PutActorById.Command(1, "Bryan Cranston updated"))

        transaction(Database.connect(datasource)) {
            val result = ActorTable.select { ActorTable.id eq 1 }.first()
            result[ActorTable.id] shouldBe 1
            result[ActorTable.name] shouldBe "Bryan Cranston updated"
            result[ActorTable.creationDate] shouldBe Instant.parse("2020-11-20T14:00:00Z")
            result[ActorTable.lastUpdateDate] shouldBe Instant.parse("2020-11-21T09:00:00Z")
        }
    }

    @Test
    fun `should throw an ActorNotFoundException`() {
        val fixedClock = object : Clock { override fun now() = Instant.parse("2020-11-20T09:00:00Z") }
        val sut = PutActorById(actorRepository, fixedClock)

        val exception = shouldThrowExactly<ActorNotFoundException> {
            sut.handle(PutActorById.Command(999, "New name"))
        }

        exception.message shouldBe "Actor does not exists with id: 999"
    }
}
