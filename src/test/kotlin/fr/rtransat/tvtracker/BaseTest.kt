package fr.rtransat.tvtracker

import fr.rtransat.tvtracker.api.TvTrackerApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [TvTrackerApplication::class])
@ActiveProfiles("test", "test-local")
abstract class BaseTest
