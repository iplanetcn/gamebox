package cherry.gamebox.bunny.game

import cherry.gamebox.bunny.util.CameraHelper
import cherry.gamebox.bunny.util.Constants
import cherry.gamebox.core.GameLogger
import com.badlogic.gdx.Application.ApplicationType
import com.badlogic.gdx.Game
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
class WorldController(game: Game) : InputAdapter() {
    var cameraHelper: CameraHelper
    var level: Level
    var lives = 0
    var score = 0

    init {
        Gdx.input.inputProcessor = this
        cameraHelper = CameraHelper()
        lives = Constants.LIVES_START
        score = 0
        level = Level(Constants.LEVEL_01)
    }

    private fun restart() {
        Gdx.input.inputProcessor = this
        cameraHelper = CameraHelper()
        lives = Constants.LIVES_START
        score = 0
        level = Level(Constants.LEVEL_01)
    }

    override fun keyUp(keycode: Int): Boolean {
        if (keycode == Input.Keys.R) {
            restart()
            GameLogger.debug("Game World reset")
        }
        return false
    }

    private fun createProceduralPixmap(width: Int, height: Int): Pixmap {
        val pixmap = Pixmap(width, height, Pixmap.Format.RGBA8888)
        pixmap.setColor(1f, 1f, 0f, 0.5f)
        pixmap.fill()
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
        cameraHelper.update(deltaTime)
    }

    private fun handleDebugInput(deltaTime: Float) {
        if (Gdx.app.type != ApplicationType.Desktop) {
            return
        }

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
            cameraHelper.setPosition(0f, 0f)
        }
        // camera Control Zoom
        var camZoomSpeed = 1 * deltaTime
        val camZoomSpeedAccelerationFactor = 5f
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            camZoomSpeed *= deltaTime
        }
        if (Gdx.input.isKeyPressed(Input.Keys.COMMA)) {
            cameraHelper.addZoom(camMoveSpeed)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SLASH)) {
            cameraHelper.addZoom(-camMoveSpeed)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            cameraHelper.setZoom(1f)
        }
    }

    private fun moveCamera(x: Float, y: Float) {
        val tx = x + cameraHelper.getPosition().x
        val ty = y + cameraHelper.getPosition().y
        cameraHelper.setPosition(tx, ty)
    }
}
