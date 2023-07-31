package fr.rtransat.tvtracker.api

import fr.rtransat.tvtracker.api.converters.SortTypeConverter
import fr.rtransat.tvtracker.domain.actor.ActorRepository
import fr.rtransat.tvtracker.domain.actor.command.CreateActor
import fr.rtransat.tvtracker.domain.actor.command.DeleteActorById
import fr.rtransat.tvtracker.domain.actor.command.PutActorById
import fr.rtransat.tvtracker.domain.actor.query.GetActorById
import fr.rtransat.tvtracker.domain.actor.query.ListActor
import jakarta.annotation.PostConstruct
import kotlinx.datetime.Clock
import org.jetbrains.exposed.spring.SpringTransactionManager
import org.jetbrains.exposed.sql.DatabaseConfig
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.*
import javax.sql.DataSource

@SpringBootApplication
class TvTrackerApplication {
    @PostConstruct
    fun setTz() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    @Bean
    fun clock(): Clock = Clock.System

    @Bean(name = ["transaction-manager-ro"])
    fun transactionManagerRO(@Qualifier("datasource-ro") dataSource: DataSource) =
        SpringTransactionManager(dataSource, DatabaseConfig { })

    @Bean(name = ["transaction-manager-rw"])
    fun transactionManagerRW(@Qualifier("datasource-rw") dataSource: DataSource) =
        SpringTransactionManager(dataSource, DatabaseConfig { })
}

@Configuration(proxyBeanMethods = false)
class DataSourcesConfiguration {
    @Bean(name = ["datasource-ro"])
    @ConfigurationProperties("app.datasource.ro")
    fun dataSourceRO(): DataSource = DataSourceBuilder.create().build()

    @Bean(name = ["datasource-rw"])
    @ConfigurationProperties("app.datasource.rw")
    fun dataSourceRW(): DataSource = DataSourceBuilder.create().build()
}

@Configuration
class DomainConfiguration {
    @Bean
    fun listActorQuery(actorRepository: ActorRepository) = ListActor(actorRepository)

    @Bean
    fun getActorByIdQuery(actorRepository: ActorRepository) = GetActorById(actorRepository)

    @Bean
    fun createActorCommand(actorRepository: ActorRepository, clock: Clock) = CreateActor(actorRepository, clock)

    @Bean
    fun putActorByIdCommand(actorRepository: ActorRepository, clock: Clock) = PutActorById(actorRepository, clock)

    @Bean
    fun deleteActorByIdCommand(actorRepository: ActorRepository) = DeleteActorById(actorRepository)
}

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(SortTypeConverter())
    }
}

fun main(args: Array<String>) {
    runApplication<TvTrackerApplication>(*args)
}
