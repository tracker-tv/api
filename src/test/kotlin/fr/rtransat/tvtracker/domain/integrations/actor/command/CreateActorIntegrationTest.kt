package fr.rtransat.tvtracker.domain.integrations.actor.command

import fr.rtransat.tvtracker.api.infrastructure.exposed.tables.ActorTable
import fr.rtransat.tvtracker.domain.actor.ActorRepository
import fr.rtransat.tvtracker.domain.actor.command.CreateActor
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

class CreateActorIntegrationTest(
    @Autowired
    resourceLoader: ResourceLoader,
    @Qualifier("datasource-rw")
    datasource: DataSource,
    @Autowired
    val actorRepository: ActorRepository
) : BaseDomainIntegrationTest(resourceLoader, datasource) {
    @Test
    fun `should create an actor`() {
        val fixedClock = object : Clock { override fun now() = Instant.parse("2020-11-20T09:00:00Z") }
        val sut = CreateActor(actorRepository, fixedClock)

        sut.handle(CreateActor.Command("Salma Hayek"))

        transaction(Database.connect(datasource)) {
            val result = ActorTable.select { ActorTable.id eq 4 }.first()
            result[ActorTable.id] shouldBe 4
            result[ActorTable.name] shouldBe "Salma Hayek"
            result[ActorTable.creationDate] shouldBe Instant.parse("2020-11-20T09:00:00Z")
            result[ActorTable.lastUpdateDate] shouldBe null
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
