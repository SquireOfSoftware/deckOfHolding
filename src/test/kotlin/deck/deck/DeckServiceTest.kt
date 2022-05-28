package deck.deck

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
internal class DeckServiceTest {
    // https://www.wwt.com/article/using-testcontainers-for-unit-tests-with-spring-and-kotlin
    // ^ doesnt work, try this one \/
    // https://rieckpil.de/testing-spring-boot-applications-with-kotlin-and-testcontainers/

    @Autowired
    private lateinit var deckJpa: DeckJpa

    companion object {

        @Container
        val container = PostgreSQLContainer<Nothing>("postgres:10").apply {
            withDatabaseName("testdb")
            withUsername("postgres")
            withPassword("password")
            start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.password", container::getPassword)
            registry.add("spring.datasource.username", container::getUsername)
            registry.add("spring.datasource.driverClassName") { "org.postgresql.Driver" }
            registry.add("spring.jpa.database-platform") {"org.hibernate.dialect.PostgreSQLDialect"}
            registry.add("spring.jpa.hibernate.ddl-auto") {"create"}
        }
    }

    @Test
    fun `when record is saved then the id is populated`() {
        val sessionId = UUID.randomUUID()
        val sampleId = UUID.randomUUID()
        val actual = deckJpa.save(Deck(sampleId, sessionId))
        assertEquals(sampleId, actual.id)
    }
}