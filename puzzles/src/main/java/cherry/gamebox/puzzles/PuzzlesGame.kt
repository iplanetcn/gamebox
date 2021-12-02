package cherry.gamebox.puzzles

import cherry.gamebox.core.Assets
import cherry.gamebox.puzzles.screens.MenuScreen
import cherry.gamebox.puzzles.screens.NotCrossScreen
import cherry.gamebox.puzzles.screens.SCREEN_HEIGHT
import cherry.gamebox.puzzles.screens.SCREEN_WIDTH
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.StretchViewport

/**
 * PuzzlesGame
 *
 * @author john
 * @since 2021-12-01
 */
class PuzzlesGame : Game() {
    lateinit var batcher: SpriteBatch
    lateinit var stage: Stage

    override fun create() {
        stage = Stage(
            StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT)
        )

        Assets.load()
        batcher = SpriteBatch()
//        setScreen(MenuScreen(this))
        setScreen(NotCrossScreen(this))
    }
}