package cherry.gamebox.solitaire

import cherry.gamebox.core.Assets
import cherry.gamebox.solitaire.screen.GameScreen
import cherry.gamebox.solitaire.screen.SCREEN_HEIGHT
import cherry.gamebox.solitaire.screen.SCREEN_WIDTH
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.StretchViewport

/**
 * Solitaire
 *
 * @author john
 * @since 2021-12-09
 */
class SolitaireGame : Game() {
    lateinit var batch: SpriteBatch
    lateinit var stage: Stage

    override fun create() {
        stage = Stage(
            StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT)
        )

        Assets.load()
        batch = SpriteBatch()
        setScreen(GameScreen(this))
    }
}