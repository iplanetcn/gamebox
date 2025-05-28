package cherry.gamebox.solitaire.model

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import java.util.UUID

/**
 * Card
 *
 * @author john
 * @since 2022-12-11
 */
abstract class Pile(
    var id: UUID = UUID.randomUUID(),
    val cardList: MutableList<Card> = mutableListOf()
): Group() {
    fun addCards(cards: MutableList<Card>) {
        for ((index, card) in cards.withIndex()) {
            card.zIndex = index + 100
            addActor(card)
            cardList.add(card)
        }
    }

    fun addCards(vararg cards: Card) {
        cardList.addAll(cards)
    }

    fun removeCards(cards: MutableList<Card>) {
        cardList.removeAll(cards)
    }

    fun removeCards(vararg cards: Card) {
        cardList.removeAll(cards.toSet())
        for (card in cards) {
            removeActor(card)
        }
    }

    override fun clear() {
        super.clear()
        cardList.clear()
    }
}