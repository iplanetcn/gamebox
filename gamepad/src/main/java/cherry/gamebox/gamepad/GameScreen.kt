package cherry.gamebox.gamepad

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

/**
 * GameScreen
 *
 * @author john
 * @since 2022-09-19
 */
class GameScreen(private val game: GamepadGame) : ScreenAdapter() {
    private var touchpad: Touchpad
    private var block: Image
    private var blockSpeed: Float

    init {
        val touchpadStyle = Touchpad.TouchpadStyle(
            TextureRegionDrawable(Texture(Gdx.files.internal("dpad.png"))),
            TextureRegionDrawable(Texture(Gdx.files.internal("knob.png"))),
        )
        touchpad = Touchpad(10f, touchpadStyle)
        touchpad.setSize(200f, 200f)
        touchpad.setBounds(15f, 15f, 200f, 200f)
        touchpad.setPosition(SCREEN_WIDTH / 2 - touchpad.width / 2, 200f)
        game.stage.addActor(touchpad)

        block = Image(Texture(Gdx.files.internal("block.png")))
        block.setSize(100f, 100f)
        block.setPosition(SCREEN_WIDTH / 2 - block.width / 2, SCREEN_HEIGHT / 2 - block.height / 2)
        game.stage.addActor(block)

        Gdx.input.inputProcessor = game.stage
        blockSpeed = 6f
    }

    override fun render(delta: Float) {
        // clear
        Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f)
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT)

        game.camera.update()

        // move block sprite with touchpad
        block.x = block.x + touchpad.knobPercentX * blockSpeed
        block.y = block.y + touchpad.knobPercentY * blockSpeed

        // draw
        game.batch.begin()
        game.batch.end()
        game.stage.act(delta)
        game.stage.draw()

    }

    override fun resize(width: Int, height: Int) {
        game.stage.viewport.update(width, height, true)
    }
}