package cherry.gamebox.snake.game

import cherry.gamebox.snake.SnakeGame
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen

/**
 * GameScreen
 *
 * @author john
 * @since 2021-11-22
 */
class GameScreen(val game: SnakeGame) : Screen {
    private var gameBoard: GameBoard = GameBoard(10,10, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

    override fun show() {
        gameBoard.renderBoard(game.shapeRenderer)
    }

    override fun render(delta: Float) {

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