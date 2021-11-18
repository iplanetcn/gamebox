package cherry.gamebox.bunny

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20

/**
 * BunnyMain
 *
 * @author john
 * @since 2020-12-06
 */
class BunnyGame : Game() {
    private lateinit var worldController: WorldController
    private lateinit var worldRenderer: WorldRenderer
    private var paused: Boolean = false

    companion object {
        val TAG = BunnyGame::class.java.simpleName
    }

    override fun create() {
        GameLogger.log("create()")
        // 将LibGDX日志级别设定为DEBUG
        GameLogger.setLogDebug()
        // 初始化控制器和渲染器
        worldController = WorldController()
        worldRenderer = WorldRenderer(worldController)
        // 启动时默认激活游戏
        paused = false
    }

    override fun resize(width: Int, height: Int) {
        GameLogger.log("resize(width,height)")
        worldRenderer.resize(width, height)
    }

    override fun render() {
        GameLogger.log("render()")
        // 当游戏暂停时,不再更新游戏世界
        if (!paused) {
            // 根据最后一帧的时间更新游戏世界
            worldController.update(Gdx.graphics.deltaTime)
        }
        // 设置清屏延时为浅蓝色
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f)
        // 清屏
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        // 将游戏世界渲染到屏幕上
        worldRenderer.render()
    }

    override fun pause() {
        GameLogger.log("pause()")
        paused = true
    }

    override fun resume() {
        GameLogger.log("resume()")
        paused = false
    }

    override fun dispose() {
        GameLogger.log("dispose()")
        worldRenderer.dispose()
    }
}