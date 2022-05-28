package deck.sessions

import deck.deck.Card
import deck.deck.Deck
import deck.deck.DeckService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class SessionService(
    val sessionJpa: SessionJpa,
    val deckService: DeckService
) {
    fun createSession(sessionRequest: SessionRequest): Pair<Session, Pair<Deck, List<Card>>> {
        val newSession = sessionJpa.save(
            Session(
                jokers = sessionRequest.jokers
            )
        )
        return Pair(
            newSession,
            if (sessionRequest.jokers) deckService.createStandardDeck(newSession.id)
            else deckService.createStandardDeckWithoutJokers(newSession.id)
        )
    }

    fun getSessions(page: Int?): List<Session> {
        return sessionJpa.findAll(PageRequest.of(page ?: 0, 100)).content
    }

    fun getSpecificSession(id: UUID): Session {
        return sessionJpa.findById(id).orElseThrow {
            RuntimeException(String.format("session of id: %s was not found", id.toString()))
        }
    }
}