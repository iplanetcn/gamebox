package cherry.gamebox.bunny.screen

import cherry.gamebox.bunny.screen.transitions.ScreenTransition
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.FrameBuffer

abstract class DirectedGame : ApplicationAdapter() {
    private var init = false
    private var currScreen: AbstractGameScreen? = null
    private var nextScreen: AbstractGameScreen? = null
    private var currFbo: FrameBuffer? = null
    private var nextFbo: FrameBuffer? = null
    private var batch: SpriteBatch? = null
    private var t = 0f
    private var screenTransition: ScreenTransition? = null

    fun setScreen(screen: AbstractGameScreen?, screenTransition: ScreenTransition? = null) {
        val w = Gdx.graphics.width
        val h = Gdx.graphics.height
        if (!init) {
            currFbo = FrameBuffer(Pixmap.Format.RGB888, w, h, false)
            nextFbo = FrameBuffer(Pixmap.Format.RGB888, w, h, false)
            batch = SpriteBatch()
            init = true
        }
        // start new transition
        nextScreen = screen
        nextScreen!!.show() // activate next screen
        nextScreen!!.resize(w, h)
        nextScreen!!.render(0f) // let next screen update() once
        if (currScreen != null) currScreen!!.pause()
        nextScreen!!.pause()
        Gdx.input.inputProcessor = null // disable input
        this.screenTransition = screenTransition
        t = 0f
    }

    override fun render() {
        // get delta time and ensure an upper limit of one 60th second
        val deltaTime = Gdx.graphics.deltaTime.coerceAtMost(1.0f / 60.0f)
        if (nextScreen == null) {
            // no ongoing transition
            if (currScreen != null) currScreen!!.render(deltaTime)
        } else {
            // ongoing transition
            var duration = 0f
            if (screenTransition != null) duration = screenTransition!!.getDuration()
            t = (t + deltaTime).coerceAtMost(duration)
            if (screenTransition == null || t >= duration) {
                // no transition effect set or transition has just finished
                if (currScreen != null) currScreen!!.hide()
                nextScreen!!.resume()
                // enable input for next screen
                Gdx.input.inputProcessor = nextScreen!!.getInputProcessor()
                // switch screens
                currScreen = nextScreen
                nextScreen = null
                screenTransition = null
            } else {
                // render screens to FBOs
                currFbo!!.begin()
                if (currScreen != null) currScreen!!.render(deltaTime)
                currFbo!!.end()
                nextFbo!!.begin()
                nextScreen!!.render(deltaTime)
                nextFbo!!.end()
                // render transition effect to screen
                val alpha = t / duration
                screenTransition?.apply {
                    render(
                        batch!!,
                        currFbo!!.colorBufferTexture,
                        nextFbo!!.colorBufferTexture,
                        alpha
                    )
                }
            }
        }
    }

    override fun resize(width: Int, height: Int) {
        if (currScreen != null) currScreen!!.resize(width, height)
        if (nextScreen != null) nextScreen!!.resize(width, height)
    }

    override fun pause() {
        if (currScreen != null) currScreen!!.pause()
    }

    override fun resume() {
        if (currScreen != null) currScreen!!.resume()
    }

    override fun dispose() {
        if (currScreen != null) currScreen!!.hide()
        if (nextScreen != null) nextScreen!!.hide()
        if (init) {
            currFbo!!.dispose()
            currScreen = null
            nextFbo!!.dispose()
            nextScreen = null
            batch!!.dispose()
            init = false
        }
    }

}