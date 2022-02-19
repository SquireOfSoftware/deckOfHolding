package deck.sessions

import deck.deck.CardDto
import deck.deck.DeckDto
import deck.deck.DeckService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/sessions")
class SessionController(val sessionService: SessionService,
                        val deckService: DeckService
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("")
    fun createSession(@RequestBody request: SessionRequest): SessionDto {
        return sessionService.createSession(request)
            .apply {  }
            .let { session -> SessionDto(
                session.first.id,
                session.first.jokers,
                DeckDto(session.second.first.id,
                        session.second.second.map { card -> CardDto(card.id, card.suit, card.type, card.type.value) })
            ) }
    }

    @GetMapping("")
    fun getSessions(@RequestParam("page", required = false) page: Int?): List<SessionDto> {
        return sessionService.getSessions(page).map { session ->
            SessionDto(session.id, session.jokers,
                deckService.getDeck(session.id).let { pair ->
                    if (pair != null)
                        DeckDto(pair.first.id,
                                pair.second.map { card -> CardDto(card.id, card.suit, card.type, card.type.value) })
                    else null }
            ) }
    }

    @GetMapping("/{sessionId}")
    fun getSession(@PathVariable sessionId: UUID): SessionDto {
        return sessionService.getSpecificSession(sessionId)
            .let { session -> SessionDto(session.id, session.jokers,
                deckService.getDeck(session.id).let {
                        pair ->
                    if (pair != null)
                        DeckDto(pair.first.id,
                            pair.second.map { card -> CardDto(card.id, card.suit, card.type, card.type.value) })
                    else null }
                )
            }
    }
}