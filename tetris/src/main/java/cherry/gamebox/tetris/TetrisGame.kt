package cherry.gamebox.tetris

import cherry.gamebox.tetris.assets.Assets
import cherry.gamebox.tetris.screens.GameScreen
import cherry.gamebox.tetris.screens.MenuScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen


/**
 * TetrisGame
 *
 * @author john
 * @since 2021-11-17
 */

const val MENU = 1
const val GAME = 2

class TetrisGame : Game() {
    lateinit var assets: Assets
    var menuScreen: MenuScreen? = null
    var gameScreen: Screen? = null

    override fun create() {
        assets = Assets()
        assets.load()

        changeScreen(MENU)
    }

    fun changeScreen(screenId: Int) {
        val screen = when (screenId) {
            MENU -> {
                menuScreen = menuScreen ?: MenuScreen(this)
                menuScreen
            }
            GAME -> {
                gameScreen = gameScreen ?: GameScreen(this)
                gameScreen
            }
            else -> null
        }

        screen?.apply { setScreen(this) }
    }

    override fun dispose() {
        super.dispose()
        assets.dispose()
    }

}
