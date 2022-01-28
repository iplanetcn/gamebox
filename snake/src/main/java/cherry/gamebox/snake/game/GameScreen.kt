package cherry.gamebox.snake.game

import cherry.gamebox.core.Assets
import cherry.gamebox.snake.SnakeGame
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera

/**
 * GameScreen
 *
 * @author john
 * @since 2021-11-22
 */
class GameScreen(private val game: SnakeGame) : Screen {
    private var gameBoard: GameBoard = GameBoard(10, 10, Gdx.graphics.width, Gdx.graphics.height)
    private val camera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

    override fun show() {
        camera.position.set(camera.viewportWidth / 2.0f, camera.viewportHeight/2.0f, 1.0f)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.298f, 0.686f, 0.314f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        gameBoard.renderBoard(game.shapeRenderer)

        // draw fps
        camera.update()
        val spriteBatch = game.batcher
        spriteBatch.projectionMatrix = camera.combined
        spriteBatch.begin()
        Assets.fonts.fontSmall.draw(spriteBatch, "Upper left, FPS=${Gdx.graphics.framesPerSecond}ms", 0f, camera.viewportHeight)
        Assets.fonts.fontSmall.draw(spriteBatch, "Lower left", 0f, Assets.fonts.fontSmall.lineHeight)
        spriteBatch.end()
    }

    override fun resize(width: Int, height: Int) {

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun dispose() {

    }
}