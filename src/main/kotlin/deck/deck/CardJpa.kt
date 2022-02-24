package deck.deck

import org.springframework.data.repository.CrudRepository
import java.util.*

interface CardJpa : CrudRepository<Card, UUID> {
    fun findByDeckId(deckId: UUID): List<Card>
    fun findFirstByDeckId(deckId: UUID): Card?
    fun findTopByDeckIdOrderByCardOrderDesc(deckId: UUID): Card?
}