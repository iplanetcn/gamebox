package cherry.gamebox.bunny.game

import cherry.gamebox.bunny.util.Constants
import cherry.gamebox.bunny.util.GamePreferences
import cherry.gamebox.bunny.util.GamePreferences.showFpsCounter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.GdxRuntimeException
import kotlin.math.max


/**
 * WordRenderer
 *
 * @author john
 * @since 2020-12-09
 */
private const val DEBUG_DRAW_BOX2D_WORLD = false

class WorldRenderer(private val worldController: WorldController) : Disposable {
    private var camera: OrthographicCamera
    private var cameraGUI: OrthographicCamera
    private var batch: SpriteBatch = SpriteBatch()
    private var b2debugRenderer: Box2DDebugRenderer
    private var shaderMonochrome: ShaderProgram

    init {
        camera = OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT)
        camera.position[0f, 0f] = 0f
        camera.update()
        cameraGUI = OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT)
        cameraGUI.position[0f, 0f] = 0f
        cameraGUI.setToOrtho(true) // flip y-axis
        cameraGUI.update()
        b2debugRenderer = Box2DDebugRenderer()
        shaderMonochrome = ShaderProgram(
            Gdx.files.internal(Constants.shaderMonochromeVertex),
            Gdx.files.internal(Constants.shaderMonochromeFragment)
        )
        if (!shaderMonochrome.isCompiled) {
            val msg = "Could not compile shader program: " + shaderMonochrome.log
            throw GdxRuntimeException(msg)
        }
    }

    fun render() {
        renderWorld(batch)
        renderGui(batch)
    }

    private fun renderWorld(batch: SpriteBatch) {
        worldController.cameraHelper.applyTo(camera)
        batch.projectionMatrix = camera.combined
        batch.begin()
        if (GamePreferences.useMonochromeShader) {
            batch.shader = shaderMonochrome
            shaderMonochrome.setUniformf("u_amount", 1.0f)
        }
        worldController.level.render(batch)
        batch.shader = null
        batch.end()
        if (DEBUG_DRAW_BOX2D_WORLD) {
            b2debugRenderer.render(worldController.b2world, camera.combined)
        }
    }

    private fun renderGui(batch: SpriteBatch) {
        batch.projectionMatrix = cameraGUI.combined
        batch.begin()

        // draw collected gold coins icon + text (anchored to top left edge)
        renderGuiScore(batch)
        // draw collected feather icon (anchored to top left edge)
        renderGuiFeatherPowerup(batch)
        // draw extra lives icon + text (anchored to top right edge)
        renderGuiExtraLive(batch)
        // draw FPS text (anchored to bottom right edge)
        if (showFpsCounter) renderGuiFpsCounter(batch)
        // draw game over text
        renderGuiGameOverMessage(batch)
        batch.end()
    }

    private fun renderGuiScore(batch: SpriteBatch) {
        val x = -15f
        val y = -15f
        var offsetX = 50f
        var offsetY = 50f
        if (worldController.scoreVisual < worldController.score) {
            val shakeAlpha = System.currentTimeMillis() % 360
            val shakeDist = 1.5f
            offsetX += MathUtils.sinDeg(shakeAlpha * 2.2f) * shakeDist
            offsetY += MathUtils.sinDeg(shakeAlpha * 2.9f) * shakeDist
        }
        batch.draw(
            Assets.goldCoin.goldCoin,
            x,
            y,
            offsetX,
            offsetY,
            100f,
            100f,
            0.35f,
            -0.35f,
            0f
        )
        Assets.fonts.defaultBig.draw(
            batch,
            "" + worldController.scoreVisual.toInt(),
            x + 75,
            y + 37
        )
    }

    private fun renderGuiFeatherPowerup(batch: SpriteBatch) {
        val x = -15f
        val y = 30f
        val timeLeftFeatherPowerup: Float = worldController.level.bunnyHead!!.timeLeftFeatherPowerup
        if (timeLeftFeatherPowerup > 0) {
            // Start icon fade in/out if the left power-up time
            // is less than 4 seconds. The fade interval is set
            // to 5 changes per second.
            if (timeLeftFeatherPowerup < 4) {
                if ((timeLeftFeatherPowerup * 5).toInt() % 2 != 0) {
                    batch.setColor(1f, 1f, 1f, 0.5f)
                }
            }
            batch.draw(
                Assets.feather.feather,
                x,
                y,
                50f,
                50f,
                100f,
                100f,
                0.35f,
                -0.35f,
                0f
            )
            batch.setColor(1f, 1f, 1f, 1f)
            Assets.fonts.defaultSmall.draw(
                batch,
                "" + timeLeftFeatherPowerup.toInt(),
                x + 60,
                y + 57
            )
        }
    }

    private fun renderGuiExtraLive(batch: SpriteBatch) {
        val x = cameraGUI.viewportWidth - 50 - Constants.LIVES_START * 50
        val y = -15f
        for (i in 0 until Constants.LIVES_START) {
            if (worldController.lives <= i) batch.setColor(0.5f, 0.5f, 0.5f, 0.5f)
            batch.draw(
                Assets.bunny.head,
                x + i * 50,
                y,
                50f,
                50f,
                120f,
                100f,
                0.35f,
                -0.35f,
                0f
            )
            batch.setColor(1f, 1f, 1f, 1f)
        }
        if (worldController.lives >= 0 && worldController.livesVisual > worldController.lives) {
            val i: Int = worldController.lives
            val alphaColor = max(0f, worldController.livesVisual - worldController.lives - 0.5f)
            val alphaScale: Float =
                0.35f * (2 + worldController.lives - worldController.livesVisual) * 2
            val alphaRotate = -45 * alphaColor
            batch.setColor(1.0f, 0.7f, 0.7f, alphaColor)
            batch.draw(
                Assets.bunny.head,
                x + i * 50,
                y,
                50f,
                50f,
                120f,
                100f,
                alphaScale,
                -alphaScale,
                alphaRotate
            )
            batch.setColor(1f, 1f, 1f, 1f)
        }
    }

    private fun renderGuiFpsCounter(batch: SpriteBatch) {
        val x = cameraGUI.viewportWidth - 55
        val y = cameraGUI.viewportHeight - 15
        val fps = Gdx.graphics.framesPerSecond
        val fpsFont: BitmapFont = Assets.fonts.defaultNormal
        if (fps >= 45) {
            // 45 or more FPS show up in green
            fpsFont.setColor(0f, 1f, 0f, 1f)
        } else if (fps >= 30) {
            // 30 or more FPS show up in yellow
            fpsFont.setColor(1f, 1f, 0f, 1f)
        } else {
            // less than 30 FPS show up in red
            fpsFont.setColor(1f, 0f, 0f, 1f)
        }
        fpsFont.draw(batch, "FPS: $fps", x, y)
        fpsFont.setColor(1f, 1f, 1f, 1f) // white
    }

    private fun renderGuiGameOverMessage(batch: SpriteBatch) {
        val x = cameraGUI.viewportWidth / 2
        val y = cameraGUI.viewportHeight / 2
        if (worldController.isGameOver()) {
            val fontGameOver: BitmapFont = Assets.fonts.defaultBig
            fontGameOver.setColor(1f, 0.75f, 0.25f, 1f)
            fontGameOver.draw(batch, "GAME OVER", x, y, 1f, Align.center, false)
            fontGameOver.setColor(1f, 1f, 1f, 1f)
        }
    }

    fun resize(width: Int, height: Int) {
        camera.viewportWidth = Constants.VIEWPORT_HEIGHT / height.toFloat() * width.toFloat()
        camera.update()
        cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT
        cameraGUI.viewportWidth =
            Constants.VIEWPORT_GUI_HEIGHT / height.toFloat() * width.toFloat()
        cameraGUI.position[cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2] = 0f
        cameraGUI.update()
    }

    override fun dispose() {
        batch.dispose()
        shaderMonochrome.dispose()
    }
}
