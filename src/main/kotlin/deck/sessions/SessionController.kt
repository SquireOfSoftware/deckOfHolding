package deck.sessions

import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/sessions")
class SessionController(val sessionService: SessionService) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("")
    fun createSession(@RequestBody request: SessionRequest): Session {
        logger.info("hello world")
        return sessionService.createSession(request)
    }

    @GetMapping("")
    fun getSessions(@RequestParam("page", required = false) page: Int?): List<Session> {
        return sessionService.getSessions(page)
    }

    @GetMapping("/{sessionId}")
    fun getSession(@PathVariable sessionId: UUID): Session{
        return sessionService.getSpecificSession(sessionId)
    }
}