package cherry.gamebox.tetris.screens

import cherry.gamebox.tetris.TetrisGame
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable
import com.badlogic.gdx.utils.viewport.ScreenViewport

/**
 * MenuScreen
 *
 * @author john
 * @since 2021-11-17
 */
class MenuScreen(private val parent: TetrisGame) : Screen {
    private val stage: Stage = Stage(ScreenViewport())

    override fun show() {
        Gdx.input.inputProcessor = stage
        val table = Table()
        table.setFillParent(true)
        table.debug = true
        stage.addActor(table)

        table.background = TiledDrawable(TextureRegionDrawable(parent.assets.backgroundTexture))
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f,0f,0f,1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(Gdx.graphics.deltaTime.coerceAtMost(1 / 30f))
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun dispose() {
        stage.dispose()
    }
}