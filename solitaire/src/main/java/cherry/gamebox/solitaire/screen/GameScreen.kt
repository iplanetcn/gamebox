package cherry.gamebox.solitaire.screen

import android.R.attr.x
import android.R.attr.y
import android.util.Log.w
import cherry.gamebox.core.GameLogger
import cherry.gamebox.solitaire.SolitaireGame
import cherry.gamebox.solitaire.config.CARD_HEIGHT
import cherry.gamebox.solitaire.config.CARD_HORIZONTAL_OFFSET
import cherry.gamebox.solitaire.config.CARD_VERTICAL_OFFSET
import cherry.gamebox.solitaire.config.CARD_WIDTH
import cherry.gamebox.solitaire.config.SCREEN_HEIGHT
import cherry.gamebox.solitaire.config.SCREEN_WIDTH
import cherry.gamebox.solitaire.model.Deck
import cherry.gamebox.solitaire.model.Stock
import cherry.gamebox.solitaire.model.Waste
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Interpolation.ElasticIn
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Logger

/**
 * GameScreen
 *
 * @author john
 * @since 2021-12-09
 */
class GameScreen(game: SolitaireGame) : BaseScreen(game) {
    private val deck: Deck = Deck()
    private val stock: Stock = Stock()
    private val waste: Waste = Waste()
    private var cardBounds: Rectangle? = null

    init {
        stock.addCards(deck.shuffle())
        val pos = Vector2(SCREEN_WIDTH - (CARD_WIDTH + CARD_HORIZONTAL_OFFSET), SCREEN_HEIGHT - CARD_HEIGHT - CARD_VERTICAL_OFFSET - notchHeight)
        stock.setPosition(pos.x, pos.y)
        stage.addActor(stock)

        val wastePos = Vector2(SCREEN_WIDTH - (CARD_WIDTH + CARD_HORIZONTAL_OFFSET) * 2, SCREEN_HEIGHT - CARD_HEIGHT - CARD_VERTICAL_OFFSET - notchHeight)
        waste.setPosition(wastePos.x, wastePos.y)
        stage.addActor(waste)
    }

    override fun draw(delta: Float) {
        cardBounds?.apply {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
            shapeRenderer.color = Color.GREEN
            shapeRenderer.rect(x, y, width, height)
            shapeRenderer.end()
        }
    }

    override fun update(delta: Float) {

    }

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        val pos = convert(x, y)
        for ((index, card) in stock.cardList.reversed().withIndex()) {
            GameLogger.log("index: $index, card: $card")
            cardBounds = card.getBounds()
            if (cardBounds?.contains(pos) == true && !card.hasActions()) {
                if (card.isFaceUp) {
                    card.addAction(Actions.sequence(
                        Actions.moveTo(waste.x - stock.x, waste.y - stock.y, 0.2f)
                    ))
                } else {
                    card.addAction(Actions.parallel(
                        Actions.moveTo(waste.x - stock.x, waste.y - stock.y, 0.4f),
                        Actions.run {
                            card.flip()
                            card.zIndex = 200 - card.zIndex
                        }
                    ))

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
        cardBounds = null
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