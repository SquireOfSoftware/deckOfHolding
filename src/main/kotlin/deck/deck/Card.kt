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
    private var cardOrder: Int = 0

    fun setCardOrder(cardOrder: Int) {
        this.cardOrder = cardOrder
    }
}
