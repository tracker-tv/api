package fr.rtransat.tvtracker.domain.integrations.actor.command

import fr.rtransat.tvtracker.api.infrastructure.exposed.tables.ActorTable
import fr.rtransat.tvtracker.domain.actor.ActorRepository
import fr.rtransat.tvtracker.domain.actor.command.DeleteActorById
import fr.rtransat.tvtracker.domain.actor.exceptions.ActorNotFoundException
import fr.rtransat.tvtracker.domain.integrations.BaseDomainIntegrationTest
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.ResourceLoader
import javax.sql.DataSource

class DeleteActorByIdIntegrationTest(
    @Autowired
    resourceLoader: ResourceLoader,
    @Qualifier("datasource-rw")
    datasource: DataSource,
    @Autowired
    val actorRepository: ActorRepository
) : BaseDomainIntegrationTest(resourceLoader, datasource) {
    @Test
    fun `should delete an actor by id`() {
        val sut = DeleteActorById(actorRepository)

        transaction(Database.connect(datasource)) {
            val result = ActorTable.select { ActorTable.id eq 3 }.first()
            result[ActorTable.id] shouldBe 3
            result[ActorTable.name] shouldBe "Anna Kendrick"
            result[ActorTable.creationDate] shouldBe Instant.parse("2020-11-20T18:20:00Z")
            result[ActorTable.lastUpdateDate] shouldBe null
        }

        sut.handle(DeleteActorById.Command(3))

        transaction(Database.connect(datasource)) {
            val result = ActorTable.select { ActorTable.id eq 3 }.firstOrNull()
            result shouldBe null
        }
    }

    @Test
    fun `should throw an ActorNotFoundException`() {
        val sut = DeleteActorById(actorRepository)

        val exception = shouldThrowExactly<ActorNotFoundException> {
            sut.handle(DeleteActorById.Command(999))
        }

        exception.message shouldBe "Actor does not exists with id: 999"
    }
}
