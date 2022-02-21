package deck.sessions

import org.springframework.data.repository.PagingAndSortingRepository

interface SessionJpa : PagingAndSortingRepository<Session, String>