package cherry.gamebox.solitaire.model

import cherry.gamebox.solitaire.screen.CARD_HEIGHT
import cherry.gamebox.solitaire.screen.CARD_WIDTH
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

/**
 * Card
 *
 * @author john
 * @since 2021-12-09
 */
class Card(
    val rank: Rank,
    val suit: Suit,
    val front: Sprite,
    val back: Sprite,
    val image: Image = Image(back),
    var isFaceUp: Boolean = false
) {

    init {
        setSize(CARD_WIDTH, CARD_HEIGHT)
        updateFace()
    }

    fun setSize(width: Float, height: Float) {
        image.setSize(width, height)
    }

    fun setPosition(x: Float, y: Float) {
        image.setPosition(x - (0.5f * CARD_WIDTH), y - (0.5f * CARD_HEIGHT))
    }

    fun turn() {
        isFaceUp = !isFaceUp
        updateFace()
    }

    private fun updateFace() {
        if (isFaceUp) {
            image.drawable = TextureRegionDrawable(front)
        } else {
            image.drawable = TextureRegionDrawable(back)
        }
    }

    override fun toString() = "Card(rank=$rank, suit=$suit)"
}