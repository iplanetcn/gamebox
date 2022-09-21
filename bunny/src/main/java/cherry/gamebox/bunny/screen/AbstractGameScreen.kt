package cherry.gamebox.bunny.screen

import cherry.gamebox.bunny.game.Assets
import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager


/**
 * AbstractGameScreen
 *
 * @author john
 * @since 2022-09-21
 */
abstract class AbstractGameScreen(val game: Game) : Screen {
    abstract override fun render(deltaTime: Float)
    abstract override fun resize(width: Int, height: Int)
    abstract override fun show()
    abstract override fun hide()
    abstract override fun pause()
    override fun resume() {
        Assets.instance.init(AssetManager())
    }

    override fun dispose() {
        Assets.instance.dispose()
    }
}