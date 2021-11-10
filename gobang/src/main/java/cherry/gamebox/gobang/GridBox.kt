package cherry.gamebox.gobang

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

/**
 * GridBox
 *
 * @author john
 * @since 2021-07-13
 */
data class GridBox(
    val index: Index,
    val pos: Vector2,
    val size: Float,
    val rect: Rectangle = Rectangle(pos.x - size / 2, pos.y - size / 2, size, size)
)