package deck.deck

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Deck(@Id @GeneratedValue val id: UUID,
           val sessionId: UUID
)