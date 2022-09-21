package cherry.gamebox.bunny.screen

import cherry.gamebox.bunny.game.Assets
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen


/**
 * AbstractGameScreen
 *
 * @author john
 * @since 2022-09-21
 */
abstract class AbstractGameScreen(val game: DirectedGame) : Screen {
    abstract override fun render(deltaTime: Float)
    abstract override fun resize(width: Int, height: Int)
    abstract override fun show()
    abstract override fun hide()
    abstract override fun pause()
    abstract fun getInputProcessor(): InputProcessor

    override fun resume() {
        Assets.load()
    }

    override fun dispose() {
        Assets.dispose()
    }
}