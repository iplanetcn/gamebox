package cherry.gamebox.solitaire.screen

import cherry.gamebox.solitaire.SolitaireGame
import cherry.gamebox.solitaire.model.Deck
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

    init {
        var i = 0
        deck.cards.forEach { card ->
            card.setPosition(CARD_WIDTH / 2 + (card.rank.value - 1) * (CARD_WIDTH + HORIZONTAL_OFFSET), 0f + CARD_HEIGHT/2)
            stage.addActor(card.image)
            i++
        }
    }

    override fun draw(delta: Float) {

    }

    override fun update(delta: Float) {

    }

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        val pos = convert(x, y)
        deck.cards.forEach { card ->
            card.turn()
            card.image.addAction(
                Actions.parallel(
                    Actions.rotateBy(360f, 0.6f),
                    Actions.moveBy(0f, 0f, 0.6f)
                )
            )
        }
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