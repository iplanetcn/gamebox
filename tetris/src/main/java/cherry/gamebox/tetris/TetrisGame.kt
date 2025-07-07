package cherry.gamebox.tetris

import cherry.gamebox.tetris.screens.MenuScreen
import cherry.gamebox.tetris.screens.SCREEN_HEIGHT
import cherry.gamebox.tetris.screens.SCREEN_WIDTH
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport

/**
 * TetrisGame
 *
 * @author john
 * @since 2021-11-17
 */
class TetrisGame : Game() {
    lateinit var batch: SpriteBatch
    lateinit var stage: Stage

    override fun create() {
        stage = Stage(
            FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT)
        )

        Assets.load()
        batch = SpriteBatch()
        setScreen(MenuScreen(this))
    }

}
