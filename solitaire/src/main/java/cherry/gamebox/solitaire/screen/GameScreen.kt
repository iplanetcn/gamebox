package cherry.gamebox.solitaire.screen

import cherry.gamebox.solitaire.SolitaireGame
import cherry.gamebox.solitaire.model.Deck
import cherry.gamebox.solitaire.model.Stack
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.actions.Actions

/**
 * GameScreen
 *
 * @author john
 * @since 2021-12-09
 */
class GameScreen(game: SolitaireGame) : BaseScreen(game) {
    private val deck: Deck = Deck()
    private val stack: Stack = Stack()

    init {
        stack.addCards(deck.shuffle())
        stack.position = Vector2(CARD_HORIZONTAL_OFFSET, SCREEN_HEIGHT - CARD_HEIGHT - CARD_HORIZONTAL_OFFSET)
        stack.display(stage)

    }

    override fun draw(delta: Float) {

    }

    override fun update(delta: Float) {

    }

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        val pos = convert(x, y)
        for (card in stack.cardList.reversed()) {
            val cardBounds = card.getBounds()
            cardBounds.setPosition(stack.position)
            if (cardBounds.contains(pos)) {
                if (card.isFaceUp) {
                    card.runAction(Actions.sequence(
                        Actions.moveBy(CARD_HORIZONTAL_OFFSET + CARD_WIDTH, 0f, 0.2f),
                        Actions.run {
                            if (stack.cardList.isNotEmpty()) stack.cardList.removeLast()
                        }
                    ))

                } else {
                    card.flip()
                }
                break
            }
        }
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    private fun convert(x: Int, y: Int): Vector2 {
        return convert(x.toFloat(), y.toFloat())
    }

    private fun convert(x: Float, y: Float): Vector2 {
        val convertedPos = Vector3()
        convertedPos.set(x, y, 0F)
        camera.unproject(convertedPos)
        return Vector2(convertedPos.x, convertedPos.y)
    }

}