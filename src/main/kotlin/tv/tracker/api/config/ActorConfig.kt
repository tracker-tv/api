package tv.tracker.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tv.tracker.api.domain.actor.ActorRepository
import tv.tracker.api.infrastructure.repositories.exposed.ActorRepositoryImpl

@Configuration
class ActorConfig {
    @Bean
    fun actorRepository(): ActorRepository = ActorRepositoryImpl()
}
