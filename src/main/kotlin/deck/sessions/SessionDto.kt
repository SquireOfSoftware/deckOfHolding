package deck.sessions

import deck.deck.DeckDto
import java.util.*

data class SessionDto(val id: UUID,
                      val jokers: Boolean,
                      val deck: DeckDto?
)
