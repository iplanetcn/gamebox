package cherry.gamebox.tilematch

import cherry.gamebox.core.GameLogger
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

/**
 * TileMatchGame
 *
 * @author john
 * @since 2022-09-19
 */
const val SCREEN_WIDTH = 480f
const val SCREEN_HEIGHT = 720f

class TileMatchGame : Game() {
    lateinit var batch: SpriteBatch
    lateinit var camera: OrthographicCamera
    lateinit var shapeRenderer: ShapeRenderer
    var paused: Boolean = false

    override fun create() {
        GameLogger.log("create()")
        // 将LibGDX日志级别设定为DEBUG
        GameLogger.setLogDebug()
        camera = OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT)
        camera.position.set(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f, 0f)
        Assets.load()
        batch = SpriteBatch()
        shapeRenderer = ShapeRenderer()
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
        batch.dispose()
        shapeRenderer.dispose()
        Assets.dispose()
    }
}