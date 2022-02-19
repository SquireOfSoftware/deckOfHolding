package deck.sessions

import deck.deck.Deck
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Session (
    @Id @GeneratedValue val id: UUID,
    val jokers: Boolean = false
)
