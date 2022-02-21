package deck.deck

import java.util.*

data class DeckDto(
    val id: UUID,
    val cards: List<CardDto>,
    val count: Int
)