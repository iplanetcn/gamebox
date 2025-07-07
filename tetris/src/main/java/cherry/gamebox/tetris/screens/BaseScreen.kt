package cherry.gamebox.tetris.screens

import cherry.gamebox.tetris.Assets
import cherry.gamebox.tetris.Settings
import cherry.gamebox.tetris.TetrisGame
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlin.math.abs

/**
 * BaseScreen
 *
 * @author john
 * @since 2021-11-19
 */
const val SCREEN_WIDTH = 1080f
const val SCREEN_HEIGHT = 1920f

abstract class BaseScreen(val game: TetrisGame) : InputAdapter(), Screen,
    GestureDetector.GestureListener {
    var camera: OrthographicCamera
    var batcher: SpriteBatch
    var stage: Stage = game.stage

    init {
        stage.clear()
        batcher = game.batch
        camera = OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT)
        camera.position.set(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f, 0f)
        setupInput()
    }

    private fun setupInput() {
        // 调整参数
        val gestureDetector = GestureDetector(20f, .5f, 2f, .5f, this)
        val input = InputMultiplexer(this, gestureDetector, stage)
        Gdx.input.inputProcessor = input
    }

    override fun render(delta: Float) {
        var fixedDelta = delta
        if (delta > .1f) fixedDelta = .1f
        update(fixedDelta)
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT)
        camera.update()
        batcher.projectionMatrix = camera.combined
        draw(fixedDelta)
        stage.act(fixedDelta)
        stage.draw()
    }

    open fun changeScreenWithFadeOut(newScreen: Class<*>, game: TetrisGame) {
        val image = Image(Assets.backgrounds.background)
        image.setSize(SCREEN_WIDTH, SCREEN_HEIGHT)
        image.color.a = 0f
        image.addAction(Actions.sequence(Actions.fadeIn(.5f), Actions.run {
            game.screen = when (newScreen) {
                GameScreen::class.java -> GameScreen(game)
                MenuScreen::class.java -> MenuScreen(game)
                HelpScreen::class.java -> HelpScreen(game)
                else -> MenuScreen(game)
            }
        }))

        stage.addActor(image)
    }

    open fun addEffectToPress(actor: Actor) {
        actor.addListener(object : InputListener() {
            override fun touchDown(
                event: InputEvent,
                x: Float,
                y: Float,
                pointer: Int,
                button: Int
            ): Boolean {
                actor.setPosition(actor.x, actor.y - 5)
                event.stop()
                return true
            }

            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                actor.setPosition(actor.x, actor.y + 5)
            }
        })
    }

    abstract fun draw(delta: Float)

    abstract fun update(delta: Float)

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun show() {}

    override fun hide() {
        Settings.save()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun dispose() {
        stage.dispose()
        batcher.dispose()
    }

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        // TODO Auto-generated method stub
        return false
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        // TODO Auto-generated method stub
        return false
    }

    override fun longPress(x: Float, y: Float): Boolean {
        // TODO Auto-generated method stub
        return false
    }

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        if (abs(velocityX) > abs(velocityY)) {
            if (velocityX > 0) {
                right()
            } else {
                left()
            }
        } else {
            if (velocityY > 0) {
                down()
            } else {
                up()
            }
        }
        return false
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        // TODO Auto-generated method stub
        return false
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        // TODO Auto-generated method stub
        return false
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        // TODO Auto-generated method stub
        return false
    }

    override fun pinch(
        initialPointer1: Vector2?,
        initialPointer2: Vector2?,
        pointer1: Vector2?,
        pointer2: Vector2?
    ): Boolean {
        // TODO Auto-generated method stub
        return false
    }

    open fun up() {}

    open fun down() {}

    open fun left() {}

    open fun right() {}

    override fun pinchStop() {}


}