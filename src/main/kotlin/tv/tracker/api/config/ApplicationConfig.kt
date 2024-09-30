package tv.tracker.api.config

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import java.util.TimeZone

@Configuration
class ApplicationConfig {
    @PostConstruct
    fun setTz() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }
}
