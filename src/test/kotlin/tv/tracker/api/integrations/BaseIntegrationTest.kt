package tv.tracker.api.integrations

import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import tv.tracker.api.integrations.utils.Resource

@SpringBootTest
class BaseIntegrationTest {
    @BeforeEach
    fun reset() {
        transaction {
            exec(Resource.asString("db/actors.sql"))
        }
    }
}
