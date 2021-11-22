package cherry.gamebox.snake

import cherry.gamebox.snake.game.GameScreen
import cherry.gamebox.snake.utils.Constants.SCREEN_HEIGHT
import cherry.gamebox.snake.utils.Constants.SCREEN_WIDTH
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.StretchViewport

/**
 * SnakeGame
 *
 * @author john
 * @since 2021-11-22
 */
class SnakeGame : Game() {
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