package cherry.gamebox.solitaire.components

import cherry.gamebox.solitaire.model.Card
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor
import java.util.UUID

/**
 * Card
 *
 * @author john
 * @since 2022-12-11
 */
abstract class Pile(
    var id: UUID = UUID.randomUUID(),
    val cardList: MutableList<CardView> = mutableListOf()
): Group() {
    fun addCards(cards: MutableList<CardView>) {
        for ((index, card) in cards.withIndex()) {
            card.zIndex = index + 100
            addActor(card)
            cardList.add(card)
        }
    }

    fun addCards(vararg cards: CardView) {
        cardList.addAll(cards )
    }

    fun removeCards(cards: MutableList<CardView>) {
        cardList.removeAll(cards)
    }

    fun removeCards(vararg cards: CardView) {
        cardList.removeAll(cards.toSet())
        for (card in cards) {
            removeActor(card)
        }
    }

    override fun clear() {
        super.clear()
        cardList.clear()
    }

    //region Stack Methods
    //----------------------------------------------------------------------------------------------
    fun peek(): CardView? {
        return cardList.lastOrNull()
    }

    fun push(cardView: CardView) {
        cardList.add(0, cardView)
    }

    fun push(pile: Pile) {
        while (pile.peek() != null) {
            pile.pop()?.apply {
                push(this)
            }
        }
    }

    fun pop(): CardView? {
        return cardList.removeLastOrNull()
    }
    //endregion
}