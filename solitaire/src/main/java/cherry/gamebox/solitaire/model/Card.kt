package cherry.gamebox.solitaire.model

import android.R.attr.height
import android.R.attr.width
import android.R.attr.x
import android.R.attr.y
import cherry.gamebox.solitaire.config.CARD_HEIGHT
import cherry.gamebox.solitaire.config.CARD_WIDTH
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.addAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import java.util.UUID

/**
 * Card
 *
 * @author john
 * @since 2021-12-09
 */
data class Card(
    val rank: Rank,
    val suit: Suit,
    var isFaceUp: Boolean = false
)