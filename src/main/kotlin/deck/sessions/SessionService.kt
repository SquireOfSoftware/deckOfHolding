package deck.sessions

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class SessionService(val sessionJpa: SessionJpa) {
    fun createSession(sessionRequest: SessionRequest): Session {
        val newSession = Session(UUID.randomUUID(), sessionRequest.jokers)
        return sessionJpa.save(newSession)
    }

    fun getSessions(page: Int?): List<Session> {
        return sessionJpa.findAll(PageRequest.of(page ?: 0, 100)).content
    }

    fun getSpecificSession(id: UUID): Session {
        return sessionJpa.findById(id).orElseThrow { RuntimeException("hello") }
    }
}