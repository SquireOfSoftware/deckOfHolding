package deck.sessions

import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/sessions")
class SessionController(val sessionService: SessionService) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("")
    fun createSession(@RequestBody request: SessionRequest): SessionDto {
        logger.info("hello world")
        return sessionService.createSession(request).let { session -> SessionDto(session.id, session.jokers) }
    }

    @GetMapping("")
    fun getSessions(@RequestParam("page", required = false) page: Int?): List<SessionDto> {
        return sessionService.getSessions(page).map { session -> SessionDto(session.id, session.jokers) }
    }

    @GetMapping("/{sessionId}")
    fun getSession(@PathVariable sessionId: UUID): SessionDto {
        return sessionService.getSpecificSession(sessionId).let { session -> SessionDto(session.id, session.jokers) }
    }
}