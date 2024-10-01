package tv.tracker.api.integrations.controllers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import tv.tracker.api.integrations.BaseIntegrationTest
import tv.tracker.api.integrations.utils.Resource

@AutoConfigureMockMvc
class ActorControllerTest(
    @Autowired private val mockMvc: MockMvc,
) : BaseIntegrationTest() {
    @Test
    fun `should return a list of actors`() {
        val expected = Resource.asString("responses/find_all.json")

        mockMvc
            .get("/actors")
            .andExpect {
                status { isOk() }
                content {
                    contentType("application/json")
                    json(expected, true)
                }
            }
    }
}
