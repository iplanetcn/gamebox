package cherry.gamebox.tetris.screens

import cherry.gamebox.core.GameLogger
import cherry.gamebox.tetris.Assets
import cherry.gamebox.tetris.TetrisGame
import cherry.gamebox.tetris.game.Config
import cherry.gamebox.tetris.game.GameBoard
import cherry.gamebox.tetris.game.NextBrick
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ExtendViewport


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

        stage = Stage(ExtendViewport(vw, vh))
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
        timeSeconds += delta
        if(timeSeconds > period){
            timeSeconds-=period
            val update = gameBoard.update()
            if (!update.first && !update.second) {
                gameOver()
                return
            }
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
        changeScreenWithFadeOut(MenuScreen::class.java, game)
    }

    override fun pause() {
        super.pause()
        gamePause()
    }

    override fun resume() {
        super.resume()
        gameStart()
    }


    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        GameLogger.log("touchDown()")
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        GameLogger.log("touchUp()")
        return true
    }

    override fun left() {
        GameLogger.log("gesture->left()")
        gameBoard.updateX(-1)
    }

    override fun right() {
        GameLogger.log("gesture->right()")
        gameBoard.updateX(1)
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        GameLogger.log("gesture->tap()")
        gameBoard.rotateBrick()
        return super.tap(x, y, count, button)
    }

    override fun down() {
        GameLogger.log("gesture->down()")
        gameBoard.dropBrick()
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        val x = Gdx.input.deltaX.toFloat()
        GameLogger.log("gesture->touchDragged($x)")
        gameBoard.updateX(x.toInt())
        return true
    }
    //endregion

}