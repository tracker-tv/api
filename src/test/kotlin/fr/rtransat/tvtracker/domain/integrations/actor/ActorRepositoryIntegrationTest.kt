package fr.rtransat.tvtracker.domain.integrations.actor

import fr.rtransat.tvtracker.api.infrastructure.exposed.tables.ActorTable
import fr.rtransat.tvtracker.domain.actor.Actor
import fr.rtransat.tvtracker.domain.actor.ActorRepository
import fr.rtransat.tvtracker.domain.integrations.BaseDomainIntegrationTest
import io.kotest.matchers.nulls.shouldBeNull
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

class ActorRepositoryIntegrationTest(
    @Autowired
    resourceLoader: ResourceLoader,
    @Qualifier("datasource-rw")
    datasource: DataSource,
    @Autowired
    val sut: ActorRepository
) : BaseDomainIntegrationTest(resourceLoader, datasource) {
    @Test
    fun `should retrieve an actor by id`() {
        sut.findById(1) shouldBe actor1
    }

    @Test
    fun `should return null with an id that does not exists`() {
        sut.findById(1000).shouldBeNull()
    }

    @Test
    fun `should insert an actor`() {
        sut.save(
            Actor(
                "Jennifer Lawrence",
                Instant.parse("2020-11-22T10:00:00Z")
            )
        )

        transaction(Database.connect(datasource)) {
            val result = ActorTable.select { ActorTable.id eq 4 }.first()
            result[ActorTable.name] shouldBe "Jennifer Lawrence"
            result[ActorTable.creationDate] shouldBe Instant.parse("2020-11-22T10:00:00Z")
        }
    }

    @Test
    fun `should update an actor`() {
        val actorToUpdate = Actor(
            2,
            "Margot Robbie",
            Instant.parse("2020-11-20T16:10:00Z"),
            null
        )

        actorToUpdate.name = "Margot Robbie updated"
        actorToUpdate.lastUpdateDate = Instant.parse("2020-11-21T09:00:00Z")

        sut.update(actorToUpdate)

        transaction(Database.connect(datasource)) {
            val result = ActorTable.select { ActorTable.id eq 2 }.first()
            result[ActorTable.id] shouldBe 2
            result[ActorTable.name] shouldBe "Margot Robbie updated"
            result[ActorTable.creationDate] shouldBe Instant.parse("2020-11-20T16:10:00Z")
            result[ActorTable.lastUpdateDate] shouldBe Instant.parse("2020-11-21T09:00:00Z")
        }
    }

    @Test
    fun `should delete an actor by id`() {
        sut.delete(3)

        transaction(Database.connect(datasource)) {
            val result = ActorTable.select { ActorTable.id eq 3 }.firstOrNull()
            result.shouldBeNull()
        }
    }
}
