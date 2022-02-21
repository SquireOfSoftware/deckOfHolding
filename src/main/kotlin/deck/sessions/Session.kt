package deck.sessions

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "sessions")
class Session(
    @Id
    var id: UUID = UUID.randomUUID(),
    var jokers: Boolean
)
