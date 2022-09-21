package cherry.gamebox.bunny.screen

import cherry.gamebox.bunny.game.WorldController
import cherry.gamebox.bunny.game.WorldRenderer
import cherry.gamebox.bunny.util.GamePreferences
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.GL20


/**
 * GameScreen
 *
 * @author john
 * @since 2022-09-21
 */
class GameScreen(game: DirectedGame) : AbstractGameScreen(game) {
    private var worldController: WorldController? = null
    private var worldRenderer: WorldRenderer? = null
    private var paused = false
    override fun render(deltaTime: Float) {
        // Do not update game world when paused.
        if (!paused) {
            // Update game world by the time that has passed
            // since last rendered frame.
            worldController!!.update(deltaTime)
        }

        // Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f)

        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Render game world to screen
        worldRenderer!!.render()
    }

    override fun resize(width: Int, height: Int) {
        worldRenderer!!.resize(width, height)
    }

    override fun show() {
        GamePreferences.load()
        worldController = WorldController(game)
        worldRenderer = WorldRenderer(worldController!!)
        Gdx.input.setCatchKey(Input.Keys.BACK, true)
    }

    override fun hide() {
        worldRenderer!!.dispose()
        Gdx.input.setCatchKey(Input.Keys.BACK, false)
    }

    override fun pause() {
        paused = true
    }

    override fun getInputProcessor(): InputProcessor {
        return worldController!!
    }

    override fun resume() {
        super.resume()
        // Only called on Android!
        paused = false
    }
}