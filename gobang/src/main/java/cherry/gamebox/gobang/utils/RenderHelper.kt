package cherry.gamebox.gobang.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import javax.microedition.khronos.opengles.GL10

/**
 * RenderHelper
 *
 * @author john
 * @since 2021-07-14
 */
object RenderHelper {
    /**
     * draw coordinate
     */
    fun renderTouchCoordinate(sr: ShapeRenderer, po: Vector2) {
        val screenWidth = Gdx.graphics.width.toFloat()
        val screenHeight = Gdx.graphics.height.toFloat()
        Gdx.gl.glEnable(GL10.GL_BLEND)
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)

        // draw box
        sr.begin(ShapeRenderer.ShapeType.Filled)
        sr.color = Color(0f, 1f, 1f, 1f)
        sr.rect(po.x - 50, po.y - 50, 100f, 100f)
        sr.end()
        Gdx.gl.glDisable(GL10.GL_BLEND)

        // draw horizontal line
        sr.begin(ShapeRenderer.ShapeType.Line)
        sr.color = Color.CYAN
        sr.line(0f, po.y, screenWidth, po.y)
        sr.end()

        // draw vertical line
        sr.begin(ShapeRenderer.ShapeType.Line)
        sr.color = Color.CYAN
        sr.line(po.x, 0f, po.x, screenHeight)
        sr.end()
    }
}