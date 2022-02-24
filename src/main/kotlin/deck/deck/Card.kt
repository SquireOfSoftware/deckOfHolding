package deck.deck

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Card(
    @Id val id: UUID = UUID.randomUUID(),
    val suit: Suit,
    val type: Type,
    val deckId: UUID
) {
    var cardOrder: Int = 0
}
