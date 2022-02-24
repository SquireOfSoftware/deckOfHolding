package deck.sessions

import deck.deck.CardDto
import deck.deck.DeckDto
import deck.deck.DeckService
import deck.deck.NewCard
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/sessions")
class SessionController(
    val sessionService: SessionService,
    val deckService: DeckService
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("")
    fun createSession(@RequestBody request: SessionRequest): SessionDto {
        return sessionService.createSession(request)
            .apply { }
            .let { session ->
                SessionDto(
                    session.first.id,
                    session.first.jokers,
                    DeckDto(
                        session.second.first.id,
                        session.second.second.map { card -> CardDto(card.id, card.suit, card.type, card.type.value) },
                        session.second.second.size
                    )
                )
            }
    }

    @GetMapping("")
    fun getSessions(@RequestParam("page", required = false) page: Int?): List<SessionDto> {
        return sessionService.getSessions(page).map { session ->
            SessionDto(session.id, session.jokers,
                deckService.getDeck(session.id).let { pair ->
                    if (pair != null)
                        DeckDto(
                            pair.first.id,
                            pair.second.map { card -> CardDto(card.id, card.suit, card.type, card.type.value) },
                            pair.second.size
                        )
                    else null
                }
            )
        }
    }

    @GetMapping("/{sessionId}")
    fun getSession(@PathVariable sessionId: UUID): SessionDto {
        return sessionService.getSpecificSession(sessionId)
            .let { session ->
                SessionDto(session.id, session.jokers,
                    deckService.getDeck(session.id).let { pair ->
                        if (pair != null)
                            DeckDto(
                                pair.first.id,
                                pair.second.map { card -> CardDto(card.id, card.suit, card.type, card.type.value) },
                                pair.second.size
                            )
                        else null
                    }
                )
            }
    }

    @PostMapping("/{sessionId}:add")
    fun addCards(@PathVariable sessionId: UUID, @RequestBody cardsToAdd: List<NewCard>): List<CardDto> {
        return deckService.addCards(sessionId, cardsToAdd)
            .map {card -> CardDto(card.id, card.suit, card.type, card.type.value)}
    }

    @PutMapping("/{sessionId}:draw")
    fun drawOneCard(@PathVariable sessionId: UUID): CardDto? {
        return deckService.drawOneCard(sessionId)
            ?.let { card -> CardDto(card.id, card.suit, card.type, card.type.value) }
    }

    @PutMapping("/{sessionId}:shuffle")
    fun shuffleSession(@PathVariable sessionId: UUID): DeckDto? {
        return deckService.shuffle(sessionId).let { (deck, cardList) ->
            DeckDto(
                deck.id,
                cardList.map { card -> CardDto(card.id, card.suit, card.type, card.type.value) },
                cardList.size
            )
        }
    }
}