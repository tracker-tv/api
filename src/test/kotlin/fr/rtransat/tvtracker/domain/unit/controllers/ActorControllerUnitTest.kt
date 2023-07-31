package fr.rtransat.tvtracker.domain.unit.controllers

import fr.rtransat.tvtracker.BaseTest
import fr.rtransat.tvtracker.api.controllers.ActorController
import fr.rtransat.tvtracker.api.controllers.responses.ActorResponse
import fr.rtransat.tvtracker.domain.actor.Actor
import fr.rtransat.tvtracker.domain.actor.query.GetActorById
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.http.ResponseEntity

class ActorControllerUnitTest : BaseTest() {
    @Test
    fun `GET actor by id should return ActorResponse`() {
        val mockActor = mockk<Actor> {
            every { id } returns 1
            every { name } returns "something"
        }
        val mockResponse = mockk<GetActorById.Response> {
            every { actor } returns mockActor
        }
        val mockRequest = mockk<GetActorById.Request> {
            every { id } returns 1
        }
        val mockQuery = mockk<GetActorById> {
            every { handle(mockRequest) } returns mockResponse
        }
        val mockContext = mockk<ApplicationContext> {
            every { getBean<GetActorById>() } returns mockQuery
        }

        val sut = ActorController(mockContext)

        val result: ResponseEntity<ActorResponse> = sut.get(1)

        verify {
            mockContext.getBean<GetActorById>()
            mockQuery.handle(mockRequest)
        }

        val body = result.body

        body.shouldNotBeNull()
        body shouldBe instanceOf<ActorResponse>()
        body.id shouldBe 1
        body.name shouldBe "something"
    }
}
