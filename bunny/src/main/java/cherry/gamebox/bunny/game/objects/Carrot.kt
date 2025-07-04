package cherry.gamebox.bunny.game.objects

import cherry.gamebox.bunny.game.Assets
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion


class Carrot : AbstractGameObject() {

    private var regCarrot: TextureRegion

    init {
        dimension[0.25f] = 0.5f
        regCarrot = Assets.levelDecoration.carrot

        // Set bounding box for collision detection
        bounds[0f, 0f, dimension.x] = dimension.y
        origin[dimension.x / 2] = dimension.y / 2
    }

    override fun render(batch: SpriteBatch) {
        val reg = regCarrot
        batch.draw(
            reg.texture,
            position.x - origin.x,
            position.y - origin.y,
            origin.x,
            origin.y,
            dimension.x,
            dimension.y,
            scale.x,
            scale.y,
            rotation,
            reg.regionX,
            reg.regionY,
            reg.regionWidth,
            reg.regionHeight,
            false,
            false
        )
    }
}