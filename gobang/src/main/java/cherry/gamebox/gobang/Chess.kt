package cherry.gamebox.gobang

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

/**
 * Chess
 *
 * @author john
 * @since 2021-07-13
 */
interface Chess {
    fun draw(shapeRenderer: ShapeRenderer, pos: Vector2, size: Float)
}