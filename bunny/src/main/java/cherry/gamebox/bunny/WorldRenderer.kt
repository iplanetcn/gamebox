package cherry.gamebox.bunny

import android.R.attr.font
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable


/**
 * WordRenderer
 *
 * @author john
 * @since 2020-12-09
 */
class WorldRenderer(private val worldController: WorldController) : Disposable {
    lateinit var camera: OrthographicCamera
    lateinit var batch: SpriteBatch
    lateinit var font : BitmapFont

    init {
        init()
    }

    fun init() {
        batch = SpriteBatch()
        camera = OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT)
        camera.position.set(0f, 0f, 0f)
        camera.update()
        font = BitmapFont(Gdx.files.internal("font/bmf1.fnt"), false)
        font.color = Color.RED
    }

    fun render() {
        renderTestObjects()
    }

    private fun renderTestObjects() {
        batch.projectionMatrix = camera.combined
        batch.begin()
        for (sprite in worldController.textSprites) {
            sprite.draw(batch)
        }
        batch.end()
    }

    fun resize(width: Int, height: Int) {
        camera.viewportWidth = Constants.VIEWPORT_HEIGHT / height * width
        camera.update()
    }

    override fun dispose() {
        batch.dispose()
    }
}