package cherry.gamebox.solitaire.model

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import java.util.*

/**
 * Card
 *
 * @author john
 * @since 2022-12-11
 */
abstract class Pile(
    var id: UUID = UUID.randomUUID(),
    var position: Vector2 = Vector2.Zero,
    val cardList: MutableList<Card> = mutableListOf()
) {

    abstract fun display(stage: Stage)

    fun addCards(cards: MutableList<Card>) {
        for ((index, card) in cards.withIndex()) {
            card.zIndex = index + 100
        }
        cardList.addAll(cards)
    }

    fun addCards(vararg cards: Card) {
        cardList.addAll(cards)
    }

    fun removeCards(cards: MutableList<Card>) {
        cardList.removeAll(cards)
    }

    fun removeCards(vararg cards: Card) {
        cardList.removeAll(cards.toSet())
    }

    fun clear() {
        cardList.clear()
    }
}