package cherry.gamebox.minesweeper.screen.transitions

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface ScreenTransition {
    fun getDuration() : Float

    fun render(batch: SpriteBatch, currScreen: Texture, nextScreen: Texture, alpha: Float)
}