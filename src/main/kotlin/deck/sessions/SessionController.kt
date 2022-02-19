package deck.sessions

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController("/session")
class SessionController(val sessionJpa: SessionJpa) {

    @PostMapping("s")
    fun createSession(session: Session): Session {
        sessionJpa.()
    }

    @GetMapping("/session")
    fun getSession(): Session = Session(UUID.randomUUID())
}