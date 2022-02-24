package deck.sessions

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import deck.deck.CardDto
import deck.deck.DeckDto
import deck.deck.DeckService
import graphql.schema.DataFetchingEnvironment

@DgsComponent
class SessionDataFetcher(
    val sessionService: SessionService,
    val deckService: DeckService
) {
    @DgsQuery(field = "session")
    fun getSession(dfe: DataFetchingEnvironment): SessionDto {
        return sessionService.getSpecificSession(dfe.getArgument("id"))
            .let { session -> SessionDto(session.id,
                deck = null,
                jokers = session.jokers
            )}
    }


}