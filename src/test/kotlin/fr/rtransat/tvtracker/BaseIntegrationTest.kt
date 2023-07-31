package fr.rtransat.tvtracker

import org.junit.jupiter.api.BeforeEach
import org.springframework.core.io.ResourceLoader
import org.springframework.jdbc.datasource.init.ScriptUtils
import javax.sql.DataSource

abstract class BaseIntegrationTest(
    protected val resourceLoader: ResourceLoader,
    protected val datasource: DataSource,
) : BaseTest() {
    @BeforeEach
    fun reset() {
        datasource.connection.use {
            ScriptUtils.executeSqlScript(
                it,
                resourceLoader.getResource("classpath:data/reset.sql")
            )

            ScriptUtils.executeSqlScript(
                it,
                resourceLoader.getResource("classpath:data/actors.sql")
            )
        }
    }
}
