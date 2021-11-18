package cherry.gamebox.bunny

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable


/**
 * WordRenderer
 *
 * @author john
 * @since 2020-12-09
 */
class WorldRenderer(worldController: WorldController) : Disposable {
    private var camera: OrthographicCamera? = null
    private var batch: SpriteBatch? = null
    private val worldController: WorldController
    private fun init() {
        batch = SpriteBatch()
        camera = OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT)
        camera!!.position[0f, 0f] = 0f
        camera!!.update()
    }

    fun render() {
        renderWorld(batch)
    }

    fun resize(width: Int, height: Int) {
        camera!!.viewportWidth = Constants.VIEWPORT_WIDTH / height * width
        camera!!.update()
    }

    override fun dispose() {
        batch!!.dispose()
    }

    private fun renderWorld(batch: SpriteBatch?) {
        worldController.cameraHelper!!.applyTo(camera!!)
        batch!!.projectionMatrix = camera!!.combined
        batch.begin()
        worldController.level!!.render(batch)
        batch.end()
    }

    init {
        this.worldController = worldController
        init()
    }
}
