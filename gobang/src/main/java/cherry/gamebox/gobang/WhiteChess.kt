package cherry.gamebox.gobang

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import javax.microedition.khronos.opengles.GL10

/**
 * WhiteChess
 *
 * @author john
 * @since 2021-07-13
 */
class WhiteChess : Chess {
    override fun draw(shapeRenderer: ShapeRenderer, pos: Vector2, size: Float) {
        Gdx.gl.glEnable(GL10.GL_BLEND)
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color(1f, 1f, 1f, 1f)
        shapeRenderer.circle(pos.x, pos.y, size / 2)
        shapeRenderer.end()
        Gdx.gl.glDisable(GL10.GL_BLEND)
    }
}