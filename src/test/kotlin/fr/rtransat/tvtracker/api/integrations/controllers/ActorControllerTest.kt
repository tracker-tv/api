package fr.rtransat.tvtracker.api.integrations.controllers

import fr.rtransat.tvtracker.BaseIntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.core.io.ResourceLoader
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import javax.sql.DataSource

@AutoConfigureMockMvc
class ActorControllerTest(
    @Autowired
    resourceLoader: ResourceLoader,
    @Qualifier("datasource-rw")
    datasource: DataSource,
    @Autowired
    val mockMvc: MockMvc,
) : BaseIntegrationTest(resourceLoader, datasource) {
    @Test
    fun `GET actor by id should return 200`() {
        val expectedJSON = """
            {
                "id": 1,
                "name": "Bryan Cranston"
            }
        """.trimIndent()

        mockMvc.get("/v1/actors/1").andExpect {
            status { isOk() }
            content { json(expectedJSON, true) }
        }.andReturn()
    }

    @Test
    fun `GET actor by id should return 404`() {
        val expectedJSON = """
            {
                "status": 404,
                "message": "Actor does not exists with id: 999"
            }
        """.trimIndent()

        mockMvc.get("/v1/actors/999").andExpect {
            status { isNotFound() }
            content { json(expectedJSON, true) }
        }.andReturn()
    }

    @Test
    fun `POST actor should return 201`() {
        val data = """
            {
                "name": "Adèle Exarchopoulos"
            }
        """.trimIndent()

        mockMvc.post("/v1/actors") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = data
        }.andExpect {
            status { isCreated() }
        }.andReturn()
    }

    @Test
    fun `POST actor should return 422`() {
        val data = """
            {
                "name": ""
            }
        """.trimIndent()

        val expectedJSON = """
            {
                "status": 422,
                "message": "Validation constraint error",
                "violations": {
                    "name": [
                        "must not be blank"
                    ]
                }
            }
        """.trimIndent()

        mockMvc.post("/v1/actors") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = data
        }.andExpect {
            status { isUnprocessableEntity() }
            content { json(expectedJSON, true) }
        }.andReturn()
    }

    @Test
    fun `PUT actor by id should return 204`() {
        val data = """
            {
                "name": "New name"
            }
        """.trimIndent()

        mockMvc.put("/v1/actors/1") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = data
        }.andExpect {
            status { isNoContent() }
        }.andReturn()
    }

    @Test
    fun `PUT actor by id should return 404`() {
        val data = """
            {
                "name": "New name"
            }
        """.trimIndent()

        val expectedJSON = """
            {
                "status": 404,
                "message": "Actor does not exists with id: 999"
            }
        """.trimIndent()

        mockMvc.put("/v1/actors/999") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = data
        }.andExpect {
            status { isNotFound() }
            content { json(expectedJSON, true) }
        }.andReturn()
    }
}
