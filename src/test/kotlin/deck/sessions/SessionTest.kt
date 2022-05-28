package deck.sessions

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Tag("integrationTest")
internal class SessionTest {
    @Autowired
    private lateinit var sessionJpa: SessionJpa

    @Autowired
    private lateinit var webTestClient: WebTestClient

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
            registry.add("spring.jpa.hibernate.ddl-auto") {"update"}
        }
    }

    @Test
    fun `when record is saved then the id is populated`() {
        val sessionId = UUID.randomUUID()
        val actual = sessionJpa.save(Session(sessionId, false))
        assertEquals(sessionId, actual.id)
    }

    @Test
    fun `when record is saved then the JSON is returned`() {
        val result = webTestClient.post()
            .uri("/sessions")
            .bodyValue(SessionRequest(false))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(SessionDto::class.java)
            .returnResult()
            .responseBody

        webTestClient.get()
            .uri("/sessions/${result.id}")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody().jsonPath("$.length()").isEqualTo(3)
    }
}