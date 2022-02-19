package deck.deck

import org.springframework.data.repository.CrudRepository
import java.util.*

interface DeckJpa : CrudRepository<Deck, UUID> {
    fun findBySessionId(sessionId: UUID): Deck
}