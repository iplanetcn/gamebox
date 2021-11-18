package cherry.gamebox.tetris

import cherry.gamebox.tetris.assets.Assets
import cherry.gamebox.tetris.screens.GameScreen
import cherry.gamebox.tetris.screens.MenuScreen
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Application.LOG_ERROR
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch


/**
 * TetrisGame
 *
 * @author john
 * @since 2021-11-17
 */

const val MENU = 1
const val GAME = 2

class TetrisGame : Game() {
    private lateinit var camera: OrthographicCamera
    private lateinit var spriteBatch: SpriteBatch
    private var menuScreen: MenuScreen? = null
    private var gameScreen: Screen? = null

    override fun create() {
        when {
            BuildConfig.DEBUG -> {
                // Set libgdx log level to DEBUG
                Gdx.app.logLevel = LOG_DEBUG
            }
            else -> {
                // Set libgdx log level to ERROR
                Gdx.app.logLevel = LOG_ERROR
            }
        }

        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()

        camera = OrthographicCamera(1f, h / w)
        Assets.load(AssetManager())
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
        Assets.dispose()
    }

}
