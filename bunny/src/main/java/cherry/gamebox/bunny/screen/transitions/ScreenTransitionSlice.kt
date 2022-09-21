package cherry.gamebox.bunny.screen.transitions

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import javax.microedition.khronos.opengles.GL10
import com.badlogic.gdx.utils.Array

object ScreenTransitionSlice : ScreenTransition {
    private const val UP = 1
    private const val DOWN = 2
    private const val UP_DOWN = 3

    private var duration = 0f
    private var direction = 0
    private var easing: Interpolation? = null
    private val sliceIndex = Array<Int>()

    fun init(
        duration: Float,
        direction: Int,
        numSlices: Int,
        easing: Interpolation
    ): ScreenTransitionSlice {
        this.duration = duration
        this.direction = direction
        this.easing = easing
        // create shuffled list of slice indices which determines the order of slice animation
        this.sliceIndex.clear()
        for (i in 0 until numSlices) sliceIndex.add(i)
        this.sliceIndex.shuffle()
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
        var x: Float
        var y = 0f
        val sliceWidth = (w / sliceIndex.size).toInt()
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT)
        batch.begin()
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
        if (easing != null) newAlpha = easing!!.apply(alpha)
        for (i in 0 until sliceIndex.size) {
            // current slice/column
            x = (i * sliceWidth).toFloat()
            // vertical displacement using randomized list of slice indices
            val offsetY = h * (1 + sliceIndex[i] / sliceIndex.size.toFloat())
            when (direction) {
                UP -> y = -offsetY + offsetY * newAlpha
                DOWN -> y = offsetY - offsetY * newAlpha
                UP_DOWN -> y = if (i % 2 == 0) {
                    -offsetY + offsetY * newAlpha
                } else {
                    offsetY - offsetY * newAlpha
                }
            }
            batch.draw(
                nextScreen,
                x,
                y,
                0f,
                0f,
                sliceWidth.toFloat(),
                h,
                1f,
                1f,
                0f,
                i * sliceWidth,
                0,
                sliceWidth,
                nextScreen.height,
                false,
                true
            )
        }
        batch.end()
    }

}