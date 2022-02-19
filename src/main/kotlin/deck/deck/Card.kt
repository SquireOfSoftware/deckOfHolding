package deck.deck

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Card (@Id @GeneratedValue val id: UUID,
            val suit: Suit,
            val type: Type,
            val deckId: UUID)
