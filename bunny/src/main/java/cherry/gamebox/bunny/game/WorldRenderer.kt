package cherry.gamebox.bunny.game

import cherry.gamebox.bunny.util.Constants
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable


/**
 * WordRenderer
 *
 * @author john
 * @since 2020-12-09
 */
class WorldRenderer(private val worldController: WorldController) : Disposable {
    private var camera: OrthographicCamera =
        OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT)
    private var batch: SpriteBatch = SpriteBatch()

    init {
        camera.position[0f, 0f] = 0f
        camera.update()
    }

    fun render() {
        renderWorld(batch)
    }

    fun resize(width: Int, height: Int) {
        camera.viewportWidth = Constants.VIEWPORT_WIDTH / height * width
        camera.update()
    }

    override fun dispose() {
        batch.dispose()
    }

    private fun renderWorld(batch: SpriteBatch) {
        worldController.cameraHelper.applyTo(camera)
        batch.projectionMatrix = camera.combined
        batch.begin()
        worldController.level.render(batch)
        batch.end()
    }
}
