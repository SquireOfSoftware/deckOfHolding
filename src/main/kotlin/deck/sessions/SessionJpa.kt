package deck.sessions

import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*

interface SessionJpa : PagingAndSortingRepository<Session, UUID>