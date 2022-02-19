package deck.sessions

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Session (
    @Id @GeneratedValue val id: UUID
)
