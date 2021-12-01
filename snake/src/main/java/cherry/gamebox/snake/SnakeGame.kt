package cherry.gamebox.snake

import cherry.gamebox.core.Assets
import cherry.gamebox.snake.game.GameScreen
import cherry.gamebox.snake.utils.Constants.SCREEN_HEIGHT
import cherry.gamebox.snake.utils.Constants.SCREEN_WIDTH
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
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
    lateinit var shapeRenderer: ShapeRenderer
    lateinit var stage: Stage

    override fun create() {
        Assets.load()
        batcher = SpriteBatch()
        shapeRenderer = ShapeRenderer()
        setScreen(GameScreen(this))
    }

    override fun render() {
        super.render()
    }

    override fun dispose() {
        batcher.dispose()
        shapeRenderer.dispose()
        stage.dispose()
        super.dispose()
    }
}