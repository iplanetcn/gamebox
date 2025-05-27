package cherry.gamebox.solitaire.model

import cherry.gamebox.solitaire.config.CARD_HEIGHT
import cherry.gamebox.solitaire.config.CARD_WIDTH
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.actions.Actions
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
    var isFaceUp: Boolean = false
) : Image() {

    init {
        setSize(CARD_WIDTH, CARD_HEIGHT)
        drawable = if (isFaceUp) {
            TextureRegionDrawable(front)
        } else {
            TextureRegionDrawable(back)
        }
    }

    fun flip() {
        isFaceUp = !isFaceUp
        addAction(
            Actions.sequence(
                Actions.parallel(
                    Actions.scaleTo(0f, 0.9f, 0.2f),
                    Actions.moveBy(width / 2, height * 0.05f, 0.2f)
                ),
                Actions.run {
                    drawable = if (isFaceUp) {
                        TextureRegionDrawable(front)
                    } else {
                        TextureRegionDrawable(back)
                    }
                },
                Actions.parallel(
                    Actions.scaleTo(1f, 1f, 0.2f),
                    Actions.moveBy(-width / 2, -height * 0.05f, 0.2f)
                ),
            )
        )
    }

    fun getBounds(): Rectangle {
        return Rectangle(x, y, width, height)
    }

    override fun toString() = "Card(rank=$rank, suit=$suit)"
}