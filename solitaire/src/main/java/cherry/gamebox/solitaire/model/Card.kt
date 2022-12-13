package cherry.gamebox.solitaire.model

import cherry.gamebox.solitaire.screen.CARD_HEIGHT
import cherry.gamebox.solitaire.screen.CARD_WIDTH
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Action
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
    val image: Image = Image(back),
    var isFaceUp: Boolean = false
) {

    init {
        setSize(CARD_WIDTH, CARD_HEIGHT)
        if (isFaceUp) {
            image.drawable = TextureRegionDrawable(front)
        }
    }

    fun setSize(width: Float, height: Float) {
        image.setSize(width, height)
    }

    fun setPosition(x: Float, y: Float) {
        image.setPosition(x - (0.5f * CARD_WIDTH), y - (0.5f * CARD_HEIGHT))
    }

    fun flip() {
        isFaceUp = !isFaceUp
        image.addAction(
            Actions.sequence(
                Actions.parallel(
                    Actions.scaleTo(0f, 0.9f,0.2f),
                    Actions.moveBy(image.width/2, image.height * 0.05f, 0.2f)
                ),
                Actions.run {
                    if (isFaceUp) {
                        image.drawable = TextureRegionDrawable(front)
                    } else {
                        image.drawable = TextureRegionDrawable(back)
                    }
                },
                Actions.parallel(
                    Actions.scaleTo(1f, 1f, 0.2f),
                    Actions.moveBy(-image.width/2, -image.height * 0.05f, 0.2f)
                ),
            )
        )
    }

    fun getBounds() : Rectangle {
       return Rectangle(image.x, image.y, image.width, image.height)
    }

    fun runAction(action: Action) {
        image.addAction(action)
    }

    override fun toString() = "Card(rank=$rank, suit=$suit)"
}