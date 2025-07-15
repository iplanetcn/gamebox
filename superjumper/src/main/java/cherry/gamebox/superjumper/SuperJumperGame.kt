package cherry.gamebox.superjumper

import cherry.gamebox.core.CoreAssets
import cherry.gamebox.superjumper.config.VIEWPORT_HEIGHT
import cherry.gamebox.superjumper.config.VIEWPORT_WIDTH
import cherry.gamebox.superjumper.controller.GameControllerManager
import cherry.gamebox.superjumper.screen.AssetsScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.StretchViewport

/**
 * Solitaire
 *
 * @author john
 * @since 2021-12-09
 */
class SuperJumperGame : Game() {
    lateinit var batch: SpriteBatch
    lateinit var stage: Stage
    lateinit var controllerManager: GameControllerManager
    lateinit var camera: OrthographicCamera

    override fun create() {
        CoreAssets.load()
        Assets.load()

        stage = Stage(StretchViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT))
        batch = SpriteBatch()
        camera = OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)
        camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f, 0f)

        setScreen(AssetsScreen(this))
        controllerManager = GameControllerManager(camera)
    }

    override fun render() {
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT)
        camera.update()
        // 设置游戏世界的投影矩阵
        batch.projectionMatrix = camera.combined
        controllerManager.update(Gdx.graphics.deltaTime)
        super.render()

        batch.begin()
        controllerManager.render(batch)
        batch.end()
    }

    override fun dispose() {
        super.dispose()
        stage.dispose()
        CoreAssets.dispose()
        Assets.dispose()
        controllerManager.dispose()
        batch.dispose()
    }
}