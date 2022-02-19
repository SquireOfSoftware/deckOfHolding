package deck.deck

import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service
class DeckService (
    private val deckJpa: DeckJpa,
    private val cardJpa: CardJpa
) {
    private fun create52CardDeck(deckId: UUID): MutableList<Card> {
        val cardList = ArrayList<Card>()
        for (suit in Suit.values().filter { suit -> suit != Suit.None }) {
            for (type in Type.values()) {
                if (type == Type.Joker) {
                    continue
                }
                cardList.add(Card(UUID.randomUUID(), suit, type, deckId))
            }
        }
        return cardList
    }

    fun createStandardDeckWithoutJokers(sessionId: UUID): Pair<Deck, List<Card>> {
        val deck = deckJpa.save(Deck(UUID.randomUUID(), sessionId))
        val cardList = create52CardDeck(deck.id)
        cardList.shuffle()
        return Pair(
            deck,
            cardJpa.saveAll(cardList).toList())
    }

    fun createStandardDeck(sessionId: UUID): Pair<Deck, List<Card>> {
        val deck = deckJpa.save(Deck(UUID.randomUUID(), sessionId))
        val cardList = create52CardDeck(deck.id)
        cardList.add(Card(UUID.randomUUID(), Suit.None, Type.Joker, deck.id))
        cardList.add(Card(UUID.randomUUID(), Suit.None, Type.Joker, deck.id))
        cardList.shuffle()
        return Pair(
            deck,
            cardJpa.saveAll(cardList).toList())
    }

    fun getDeck(sessionId: UUID): Pair<Deck, List<Card>>? {
        val deck = deckJpa.findBySessionId(sessionId)
        val cardList = cardJpa.findByDeckId(deck.id)
        return Pair(deck, cardList)
    }

    fun drawOneCard(sessionId: UUID): Card? {
        // we want to grab the last card on the list (bottom of the list)
        // and delete it from the db and return it
        val deck = deckJpa.findBySessionId(sessionId)
        val firstCard = cardJpa.findFirstByDeckId(deck.id)
        if (firstCard != null) cardJpa.delete(firstCard)
        return firstCard
    }
}