package fr.rtransat.tvtracker.domain.unit.actor.command

import fr.rtransat.tvtracker.BaseTest
import fr.rtransat.tvtracker.domain.actor.Actor
import fr.rtransat.tvtracker.domain.actor.ActorRepository
import fr.rtransat.tvtracker.domain.actor.command.CreateActor
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.junit.jupiter.api.Test

class CreateActorUnitTest : BaseTest() {
    @Test
    fun `should create an actor`() {
        val actor = mockk<Actor> {
            every { id } returns 1
        }
        val actorRepository = mockk<ActorRepository> {
            every { save(any()) } returns actor
        }
        val clock = mockk<Clock> {
            every { now() } returns mockk<Instant>()
        }

        val sut = CreateActor(actorRepository, clock)
        val responseId = sut.handle(CreateActor.Command("Actor name"))

        verify {
            actorRepository.save(any())
        }

        responseId shouldBe 1
    }
}
