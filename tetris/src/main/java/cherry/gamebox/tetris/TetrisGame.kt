package cherry.gamebox.tetris

import cherry.gamebox.tetris.assets.Assets
import cherry.gamebox.tetris.screens.GameScreen
import cherry.gamebox.tetris.screens.SCREEN_HEIGHT
import cherry.gamebox.tetris.screens.SCREEN_WIDTH
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.StretchViewport

/**
 * TetrisGame
 *
 * @author john
 * @since 2021-11-17
 */
class TetrisGame : Game() {
    lateinit var batcher: SpriteBatch
    lateinit var stage: Stage

    override fun create() {
        stage = Stage(
            StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT)
        )

        Assets.load()
        batcher = SpriteBatch()
        setScreen(GameScreen(this))
    }

}
