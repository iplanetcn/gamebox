package cherry.gamebox.solitaire.model

import cherry.gamebox.core.Assets
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
        val back = Assets.cards.backs[0]
        for (suit in Suit.all) {
            for (rank in Rank.all) {
                val front : Sprite = when(suit) {
                    Suit.SPADES -> Assets.cards.spades[rank.value - 1]
                    Suit.CLUBS -> Assets.cards.clubs[rank.value - 1]
                    Suit.HEARTS -> Assets.cards.hearts[rank.value - 1]
                    Suit.DIAMONDS -> Assets.cards.diamonds[rank.value - 1]
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