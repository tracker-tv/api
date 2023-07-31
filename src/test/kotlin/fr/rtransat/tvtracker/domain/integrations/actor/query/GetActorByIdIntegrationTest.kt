package fr.rtransat.tvtracker.domain.integrations.actor.query

import fr.rtransat.tvtracker.domain.actor.exceptions.ActorNotFoundException
import fr.rtransat.tvtracker.domain.actor.query.GetActorById
import fr.rtransat.tvtracker.domain.integrations.BaseDomainIntegrationTest
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.ResourceLoader
import javax.sql.DataSource

class GetActorByIdIntegrationTest(
    @Autowired
    resourceLoader: ResourceLoader,
    @Qualifier("datasource-rw")
    datasource: DataSource,
    @Autowired
    val sut: GetActorById
) : BaseDomainIntegrationTest(resourceLoader, datasource) {
    @Test
    fun `should return a GetActorByIdResponse`() {
        val excepted = GetActorById.Response(actor1)

        val response = sut.handle(GetActorById.Request(1))

        response shouldBe excepted
    }

    @Test
    fun `should throw an ActorNotFoundException`() {
        val exception = shouldThrowExactly<ActorNotFoundException> {
            sut.handle(GetActorById.Request(999))
        }

        exception.message shouldBe "Actor does not exists with id: 999"
    }
}
