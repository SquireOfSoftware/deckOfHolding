package deck.deck

import org.springframework.data.repository.CrudRepository
import java.util.*

interface CardJpa : CrudRepository<Card, UUID> {
    fun findByDeckId(deckId: UUID): List<Card>
}