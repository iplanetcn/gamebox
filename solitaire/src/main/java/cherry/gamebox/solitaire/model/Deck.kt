package cherry.gamebox.solitaire.model

import cherry.gamebox.core.CoreAssets
import com.badlogic.gdx.graphics.g2d.Sprite

/**
 * Deck
 *
 * @author john
 * @since 2021-12-09
 */
class Deck {
    val allCards: MutableList<Card> = ArrayList()

    init {
        val back = CoreAssets.cards.backs[0]
        for (suit in Suit.all) {
            for (rank in Rank.all) {
                val front : Sprite = when(suit) {
                    Suit.SPADES -> CoreAssets.cards.spades[rank.value - 1]
                    Suit.CLUBS -> CoreAssets.cards.clubs[rank.value - 1]
                    Suit.HEARTS -> CoreAssets.cards.hearts[rank.value - 1]
                    Suit.DIAMONDS -> CoreAssets.cards.diamonds[rank.value - 1]
                }

                val card = Card(rank, suit, front, back)
                allCards.add(card)
            }
        }
    }

    fun shuffle(): MutableList<Card> {
        allCards.shuffle()
        return allCards
    }
}