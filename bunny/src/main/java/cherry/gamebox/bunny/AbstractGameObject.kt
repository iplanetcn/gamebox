package cherry.gamebox.bunny

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2


abstract class AbstractGameObject(
    var position: Vector2 = Vector2(),
    var dimension: Vector2 = Vector2(1f, 1f),
    protected var origin: Vector2 = Vector2(),
    protected var scale: Vector2 = Vector2(1f, 1f),
    protected var rotation: Float = 0f
) {

    open fun update(deltaTime: Float) {}

    abstract fun render(batch: SpriteBatch)
}
