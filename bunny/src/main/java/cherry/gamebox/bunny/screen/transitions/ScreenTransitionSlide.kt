package cherry.gamebox.bunny.screen.transitions

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import javax.microedition.khronos.opengles.GL10


object ScreenTransitionSlide : ScreenTransition{
    private const val LEFT = 1
    private const val RIGHT = 2
    private const val UP = 3
    const val DOWN = 4


    private var duration = 0f
    private var direction = 0
    private var slideOut = false
    private var easing: Interpolation? = null

    fun init(
        duration: Float,
        direction: Int,
        slideOut: Boolean,
        easing: Interpolation?
    ): ScreenTransitionSlide {
        this.duration = duration
        this.direction = direction
        this.slideOut = slideOut
        this.easing = easing
        return this
    }

    override fun getDuration(): Float {
        return duration
    }

    override fun render(
        batch: SpriteBatch,
        currScreen: Texture,
        nextScreen: Texture,
        alpha: Float
    ) {
        var newAlpha = alpha
        val w = currScreen.width.toFloat()
        val h = currScreen.height.toFloat()
        var x = 0f
        var y = 0f
        if (easing != null) newAlpha = easing!!.apply(alpha)
        when (direction) {
            LEFT -> {
                x = -w * newAlpha
                if (!slideOut) x += w
            }
            RIGHT -> {
                x = w * newAlpha
                if (!slideOut) x -= w
            }
            UP -> {
                y = h * newAlpha
                if (!slideOut) y -= h
            }
            DOWN -> {
                y = -h * newAlpha
                if (!slideOut) y += h
            }
        }

        // drawing order depends on slide type ('in' or 'out')
        val texBottom = if (slideOut) nextScreen else currScreen
        val texTop = if (slideOut) currScreen else nextScreen

        // finally, draw both screens
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT)
        batch.begin()
        batch.draw(
            texBottom,
            0f,
            0f,
            0f,
            0f,
            w,
            h,
            1f,
            1f,
            0f,
            0,
            0,
            currScreen.width,
            currScreen.height,
            false,
            true
        )
        batch.draw(
            texTop,
            x,
            y,
            0f,
            0f,
            w,
            h,
            1f,
            1f,
            0f,
            0,
            0,
            nextScreen.width,
            nextScreen.height,
            false,
            true
        )
        batch.end()
    }
}