package deck.deck

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "decks")
class Deck(
    @Id
    var id: UUID = UUID.randomUUID(),
    var sessionId: UUID
)