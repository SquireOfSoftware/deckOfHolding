package deck.deck

import java.util.*

data class CardDto(
    val id: UUID,
    val suit: Suit,
    val type: Type,
    val value: Int
)