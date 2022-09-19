package cherry.gamebox.gamepad

import cherry.gamebox.core.Assets
import cherry.gamebox.core.GameLogger
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.StretchViewport

/**
 * TileMatchGame
 *
 * @author john
 * @since 2022-09-19
 */
val SCREEN_WIDTH = Gdx.graphics.width.toFloat()
val SCREEN_HEIGHT = Gdx.graphics.height.toFloat()

class GamepadGame : Game() {
    lateinit var batch: SpriteBatch
    lateinit var stage: Stage
    lateinit var camera: OrthographicCamera
    private var paused: Boolean = false

    override fun create() {
        GameLogger.log("create()")
        // 将LibGDX日志级别设定为DEBUG
        GameLogger.setLogDebug()
        stage = Stage(
            StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT)
        )
        camera = OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT)
        camera.position.set(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f, 0f)
        Assets.load()
        batch = SpriteBatch()
        setScreen(GameScreen(this))
        // 启动时默认激活游戏
        paused = false
    }

    override fun pause() {
        super.pause()
        GameLogger.log("pause()")
        paused = true
    }

    override fun resume() {
        super.resume()
        GameLogger.log("resume()")
        paused = false
    }

    override fun dispose() {
        super.dispose()
        GameLogger.log("dispose()")
        stage.dispose()
        batch.dispose()
    }
}