package cherry.gamebox.solitaire.components

import cherry.gamebox.core.CoreAssets
import cherry.gamebox.solitaire.config.CARD_HEIGHT
import cherry.gamebox.solitaire.config.CARD_WIDTH
import cherry.gamebox.solitaire.model.Card
import cherry.gamebox.solitaire.model.Suit
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

class CardView(
    val card: Card
) : Image() {
    val front: Sprite
    val back: Sprite

    init {
        setSize(CARD_WIDTH, CARD_HEIGHT)
        back = CoreAssets.cards.backs[0]
        front = when (card.suit) {
            Suit.SPADES -> CoreAssets.cards.spades[card.rank.value - 1]
            Suit.CLUBS -> CoreAssets.cards.clubs[card.rank.value - 1]
            Suit.HEARTS -> CoreAssets.cards.hearts[card.rank.value - 1]
            Suit.DIAMONDS -> CoreAssets.cards.diamonds[card.rank.value - 1]
        }
        drawable = if (card.isFaceUp) {
            TextureRegionDrawable(front)
        } else {
            TextureRegionDrawable(back)
        }
    }

    fun flip() {
        card.isFaceUp = !card.isFaceUp
        addAction(
            Actions.sequence(
                Actions.parallel(
                    Actions.scaleTo(0f, 0.9f, 0.2f),
                    Actions.moveBy(width / 2, height * 0.05f, 0.2f)
                ),
                Actions.run {
                    drawable = if (card.isFaceUp) {
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
        val localToStageCoordinates = localToStageCoordinates(Vector2(x, y))
        return Rectangle(localToStageCoordinates.x, localToStageCoordinates.y, width, height)
    }

    override fun toString() = "Card(rank=$card.rank, suit=$card.suit)"
}


