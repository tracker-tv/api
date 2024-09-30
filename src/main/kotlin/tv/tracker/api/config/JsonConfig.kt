package tv.tracker.api.config

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JsonConfig {
    @Bean
    fun json() =
        Json {
            namingStrategy = JsonNamingStrategy.SnakeCase
        }
}
