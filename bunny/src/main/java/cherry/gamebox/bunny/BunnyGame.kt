package cherry.gamebox.bunny

import cherry.gamebox.bunny.game.Assets
import cherry.gamebox.bunny.screen.MenuScreen
import cherry.gamebox.core.GameLogger
import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager


/**
 * BunnyMain
 *
 * @author john
 * @since 2020-12-06
 */
class BunnyGame : Game() {
    override fun create() {
        // Set Libgdx log level
        GameLogger.setLogDebug()
        // Load assets
        Assets.instance.init(AssetManager())
        // Start game at menu screen
        setScreen(MenuScreen(this))
    }

}