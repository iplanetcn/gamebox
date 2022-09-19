package cherry.gamebox.tilematch

import cherry.gamebox.core.GameLogger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image

/**
 * GameScreen
 *
 * @author john
 * @since 2022-09-19
 */
class GameScreen(private val game: TileMatchGame) : ScreenAdapter() {
    private var block: Image

    init {
        val first = Assets.atlasRegionCakeList.first()
        block = Image(first)
        block.setSize(100f, 100f)
        block.setPosition(SCREEN_WIDTH / 2 - block.width / 2, SCREEN_HEIGHT / 2 - block.height / 2)
        game.stage.addActor(block)
        Gdx.input.inputProcessor = game.stage
        game.stage.addListener(object : InputListener() {
            override fun touchDown(
                event: InputEvent?,
                x: Float,
                y: Float,
                pointer: Int,
                button: Int
            ): Boolean {
                GameLogger.log("touchDown: $x, $y; pointer: $pointer; button: $button" )
                return true
            }
        })
    }

    override fun render(delta: Float) {
        // clear
        Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f)
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT)

        game.camera.update()

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