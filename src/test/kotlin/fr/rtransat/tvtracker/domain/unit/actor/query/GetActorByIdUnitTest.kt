package fr.rtransat.tvtracker.domain.unit.actor.query

import fr.rtransat.tvtracker.BaseTest
import fr.rtransat.tvtracker.domain.actor.Actor
import fr.rtransat.tvtracker.domain.actor.ActorRepository
import fr.rtransat.tvtracker.domain.actor.exceptions.ActorNotFoundException
import fr.rtransat.tvtracker.domain.actor.query.GetActorById
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class GetActorByIdUnitTest : BaseTest() {
    @Test
    fun `should return a GetActorById response`() {
        val actor = mockk<Actor>()
        val actorRepository = mockk<ActorRepository>() {
            every { findById(1) } returns actor
        }

        val sut = GetActorById(actorRepository)

        val response = sut.handle(GetActorById.Request(1))

        verify {
            actorRepository.findById(1)
        }

        response shouldBe instanceOf<GetActorById.Response>()
        response.actor shouldBe actor
    }

    @Test
    fun `should throw a ActorNotFoundException`() {
        val actorRepository = mockk<ActorRepository>() {
            every { findById(999) } returns null
        }

        val sut = GetActorById(actorRepository)

        val exception = shouldThrowExactly<ActorNotFoundException> {
            sut.handle(GetActorById.Request(999))
        }

        verify {
            actorRepository.findById(999)
        }

        exception.message shouldBe "Actor does not exists with id: 999"
    }
}
