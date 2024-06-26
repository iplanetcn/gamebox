package cherry.gamebox.bunny.screen.transitions

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.utils.ScreenUtils

object ScreenTransitionFade : ScreenTransition {

    private var duration: Float = 0.0f

    fun init(duration: Float): ScreenTransitionFade {
        this.duration = duration
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
        val w = currScreen.width.toFloat()
        val h = currScreen.height.toFloat()
        val newAlpha = Interpolation.fade.apply(alpha)

        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1.0f)
        batch.begin()
        batch.setColor(1f, 1f, 1f, 1f)
        batch.draw(
            currScreen,
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
        batch.setColor(1f, 1f, 1f, newAlpha)
        batch.draw(
            nextScreen,
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
            nextScreen.width,
            nextScreen.height,
            false,
            true
        )
        batch.end()
    }
}