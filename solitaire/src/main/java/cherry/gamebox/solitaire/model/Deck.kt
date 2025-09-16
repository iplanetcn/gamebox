package cherry.gamebox.solitaire.model

/**
 * Deck
 *
 * @author john
 * @since 2021-12-09
 */
class Deck {
    val allCards: MutableList<Card> = mutableListOf()

    init {
        reset()
    }

    fun reset() {
        allCards.clear()
        for (suit in Suit.all) {
            for (rank in Rank.all) {
                val card = Card(rank, suit, false)
                allCards.add(card)
            }
        }
        shuffle()
    }

    fun shuffle(): MutableList<Card> {
        allCards.shuffle()
        return allCards
    }

    fun draw(): Card? {
        return allCards.removeLastOrNull()
    }
}