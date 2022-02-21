package deck.deck

import org.springframework.stereotype.Service
import java.util.*

@Service
class DeckService(
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
                cardList.add(Card(suit = suit, type = type, deckId = deckId))
            }
        }
        return cardList
    }

    private fun addCardOrder(cardList: MutableList<Card>) {
        for ((counter, card) in cardList.withIndex()) {
            card.setCardOrder(counter)
        }
    }

    fun createStandardDeckWithoutJokers(sessionId: UUID): Pair<Deck, List<Card>> {
        val deck = deckJpa.save(Deck(sessionId = sessionId))
        val cardList = create52CardDeck(deck.id)
        cardList.shuffle()
        addCardOrder(cardList)
        return Pair(
            deck,
            cardJpa.saveAll(cardList).toList()
        )
    }

    fun createStandardDeck(sessionId: UUID): Pair<Deck, List<Card>> {
        val deck = deckJpa.save(Deck(sessionId = sessionId))
        val cardList = create52CardDeck(deck.id)
        cardList.add(Card(suit = Suit.None, type = Type.Joker, deckId = deck.id))
        cardList.add(Card(suit = Suit.None, type = Type.Joker, deckId = deck.id))
        cardList.shuffle()
        addCardOrder(cardList)
        return Pair(
            deck,
            cardJpa.saveAll(cardList).toList()
        )
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

    fun shuffle(sessionId: UUID): Pair<Deck, List<Card>> {
        val deck = deckJpa.findBySessionId(sessionId)
        val cardList = cardJpa.findByDeckId(deck.id).toMutableList()
        cardList.shuffle()
        addCardOrder(cardList)
        return Pair(
            deck,
            cardJpa.saveAll(cardList).toList()
        )
    }
}