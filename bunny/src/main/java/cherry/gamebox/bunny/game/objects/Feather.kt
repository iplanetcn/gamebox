package cherry.gamebox.bunny.game.objects

import cherry.gamebox.bunny.game.Assets
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion


/**
 * Feather
 *
 * @author john
 * @since 2022-09-21
 */
class Feather : AbstractGameObject() {
    private var regFeather: TextureRegion
    var collected = false

    init {
        dimension[0.5f] = 0.5f
        regFeather = Assets.feather.feather

        // Set bounding box for collision detection
        bounds[0f, 0f, dimension.x] = dimension.y
        collected = false
    }

    override fun render(batch: SpriteBatch) {
        if (collected) return
        batch.draw(
            regFeather.texture,
            position.x,
            position.y,
            origin.x,
            origin.y,
            dimension.x,
            dimension.y,
            scale.x,
            scale.y,
            rotation,
            regFeather.regionX,
            regFeather.regionY,
            regFeather.regionWidth,
            regFeather.regionHeight,
            false,
            false
        )
    }

    fun getScore(): Int {
        return 250
    }
}