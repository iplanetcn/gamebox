package cherry.gamebox.bunny

import cherry.gamebox.bunny.game.Assets
import cherry.gamebox.bunny.screen.DirectedGame
import cherry.gamebox.bunny.screen.MenuScreen
import cherry.gamebox.bunny.util.AudioManager
import cherry.gamebox.bunny.util.GamePreferences
import cherry.gamebox.core.GameLogger


/**
 * BunnyMain
 *
 * @author john
 * @since 2020-12-06
 */
class BunnyGame : DirectedGame() {
    override fun create() {
        // Set Libgdx log level
        GameLogger.setLogDebug()
        // Load assets
        Assets.load()
        GamePreferences.load()
        AudioManager.play(Assets.music.song01)
        // Start game at menu screen
        setScreen(MenuScreen(this))
    }

}