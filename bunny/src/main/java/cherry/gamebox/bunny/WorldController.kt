package cherry.gamebox.bunny

import com.badlogic.gdx.Application.ApplicationType
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Pixmap





/**
 * WorldController
 *
 * @author john
 * @since 2020-12-09
 */
class WorldController : InputAdapter() {
    var cameraHelper: CameraHelper? = null
    var level: Level? = null
    var lives = 0
    var score = 0
    private fun initLevel() {
        score = 0
        level = Level(Constants.LEVEL_01)
    }

    private fun init() {
        Gdx.input.inputProcessor = this
        cameraHelper = CameraHelper()
        lives = Constants.LIVES_START
        initLevel()
    }

    override fun keyUp(keycode: Int): Boolean {
        if (keycode == Input.Keys.R) {
            init()
            Gdx.app.debug(TAG, "Game World resetted")
        }
        return false
    }

    private fun createProceduralPixmap(width: Int, height: Int): Pixmap {
        val pixmap = Pixmap(width, height, Pixmap.Format.RGBA8888)
        pixmap.setColor(1f, 1f, 0f, 0.5f)
        pixmap.fill() // to mau hoan toan cho cai pixmap do
        pixmap.setColor(1f, 1f, 0f, 1f)
        pixmap.setColor(1f, 1f, 0f, 1f)
        pixmap.drawLine(0, 0, width, height)
        pixmap.drawLine(width, 0, 0, height)
        pixmap.setColor(0f, 1f, 1f, 1f)
        pixmap.drawRectangle(0, 0, width, height)
        return pixmap
    }

    fun update(deltaTime: Float) {
        handleDebugInput(deltaTime)
        cameraHelper?.update(deltaTime)
    }

    private fun handleDebugInput(deltaTime: Float) {
        if (Gdx.app.type != ApplicationType.Desktop) {
            return
        }


        // thiet lap cho camera chay theo khi minh di chuyen em no :v
        var camMoveSpeed = 5 * deltaTime
        val camMoveSpeedAccelerationFactor = 5f
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            camMoveSpeed *= camMoveSpeedAccelerationFactor
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveCamera(-camMoveSpeed, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveCamera(camMoveSpeed, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveCamera(0f, camMoveSpeed)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveCamera(0f, -camMoveSpeed)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            cameraHelper?.setPosition(0f, 0f)
        }
        // camera Control Zoom
        var camZoomSpeed = 1 * deltaTime
        val camZoomSpeedAccelerationFactor = 5f
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            camZoomSpeed *= deltaTime
        }
        if (Gdx.input.isKeyPressed(Input.Keys.COMMA)) {
            cameraHelper?.addZoom(camMoveSpeed)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SLASH)) {
            cameraHelper?.addZoom(-camMoveSpeed)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            cameraHelper?.setZoom(1f)
        }
    }

    private fun moveCamera(x: Float, y: Float) {
        val tx = x + cameraHelper?.getPosition()!!.x
        val ty = y + cameraHelper?.getPosition()!!.y
        cameraHelper?.setPosition(tx, ty)
    }

    companion object {
        private val TAG = WorldController::class.java.name
    }

    init {
        init()
    }
}
