package cherry.gamebox.tetris.screens

import cherry.gamebox.tetris.TetrisGame
import cherry.gamebox.tetris.assets.Assets
import cherry.gamebox.tetris.game.Config
import cherry.gamebox.tetris.game.GameBoard
import cherry.gamebox.tetris.game.NextBrick
import cherry.gamebox.tetris.utils.GameLogger
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.StretchViewport

/**
 * GameScreen
 *
 * @author john
 * @since 2021-11-17
 */
enum class GameState {
    Stop, Play, Pause
}

class GameScreen(game: TetrisGame) : BaseScreen(game) {
    var gameBoard: GameBoard
    var nextBrick: NextBrick
    var gameState: GameState = GameState.Stop
    private var timeSeconds = 0f
    private val period = 0.5f

    init {
        val vw = (Config.Cols + 8f) * Assets.blockWidth
        val vh = vw * SCREEN_HEIGHT / (SCREEN_WIDTH * 1.0f)

        stage = Stage(StretchViewport(vw, vh))
        gameBoard = GameBoard()
        gameBoard.setPosition(
            Assets.blockWidth * 1f, Assets.blockHeight * 3f,
        )

        nextBrick = NextBrick()
        nextBrick.setPosition(
            (Config.Cols + 3f) * Assets.blockWidth,
            (Config.Rows - 12f) * Assets.blockHeight
        )

        stage.addActor(gameBoard)
        stage.addActor(nextBrick)
        gamePrepare()
        gameStart()
    }

    override fun draw(delta: Float) {
        batcher.begin()
        batcher.draw(Assets.backgrounds.background, 0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT)
        batcher.end()
    }

    override fun update(delta: Float) {
        timeSeconds += delta;
        if(timeSeconds > period){
            timeSeconds-=period;
            gameBoard.update()
            gameBoard.updateBrick()
            nextBrick.updateBrick()
        }
    }

    fun gamePrepare() {
        gameState = GameState.Stop
        gameBoard.generateBrick()
    }

    fun gameStart(){
        gameState = GameState.Play
        Assets.playMusic()
    }

    fun gamePause() {
        gameState = GameState.Pause
        Assets.pauseMusic()
    }

    fun gameStop() {
        gameState = GameState.Stop
        Assets.stopMusic()
    }

    fun gameOver() {
        gameState = GameState.Stop
        Assets.stopMusic()
        Assets.playSoundGameOver()
    }

    override fun pause() {
        super.pause()
        gamePause()
    }

    override fun resume() {
        super.resume()
        gameStart()
    }


    //region touches handler
    override fun left() {
        GameLogger.log("gesture->left()")
        gameBoard.updateX(-1)
    }

    override fun right() {
        GameLogger.log("gesture->right()")
        gameBoard.updateX(1)
    }

    override fun up() {
        GameLogger.log("gesture->up()")
        gameBoard.rotateBrick()
    }
    //endregion

}