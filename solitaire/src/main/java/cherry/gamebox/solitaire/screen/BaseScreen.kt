package cherry.gamebox.solitaire.screen

import cherry.gamebox.core.Assets
import cherry.gamebox.solitaire.SolitaireGame
import cherry.gamebox.solitaire.config.SCREEN_HEIGHT
import cherry.gamebox.solitaire.config.SCREEN_WIDTH
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

var notchHeight = 0f

abstract class BaseScreen(val game: SolitaireGame) : InputAdapter(), Screen,
    GestureDetector.GestureListener {
    var camera: OrthographicCamera
    var batch: SpriteBatch = game.batch
    var stage: Stage = game.stage

    init {
        stage.clear()
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
        batch.projectionMatrix = camera.combined
        // draw background
        batch.begin()
        batch.draw(Assets.backgrounds.background, 0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT)
        batch.end()
        // screen draw
        draw(fixedDelta)
        // stage draw
        stage.act(fixedDelta)
        stage.draw()
    }

    open fun changeScreenWithFadeOut(newScreen: Class<*>, game: SolitaireGame) {
        val image = Image(Assets.backgrounds.background)
        image.setSize(SCREEN_WIDTH, SCREEN_HEIGHT)
        image.color.a = 0f
        image.addAction(Actions.sequence(Actions.fadeIn(.5f), Actions.run {
            game.screen = when (newScreen) {
                GameScreen::class.java -> GameScreen(game)
                MenuScreen::class.java -> MenuScreen(game)
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

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun dispose() {
        stage.dispose()
        batch.dispose()
        Assets.dispose()
    }

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        return false
    }

    override fun longPress(x: Float, y: Float): Boolean {
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
        return false
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        return false
    }

    override fun pinch(
        initialPointer1: Vector2?,
        initialPointer2: Vector2?,
        pointer1: Vector2?,
        pointer2: Vector2?
    ): Boolean {
        return false
    }

    open fun up() {}

    open fun down() {}

    open fun left() {}

    open fun right() {}

    override fun pinchStop() {}


}