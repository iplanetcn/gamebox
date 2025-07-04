package cherry.gamebox.bunny.game.objects

import cherry.gamebox.bunny.game.Assets
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion


class WaterOverlay(length: Float) : AbstractGameObject() {
    private var regWaterOverlay: TextureRegion

    init {
        dimension[length * 10] = 3f
        regWaterOverlay = Assets.levelDecoration.waterOverlay
        origin.x = -dimension.x / 2
    }

    override fun render(batch: SpriteBatch) {
        batch.draw(
            regWaterOverlay.texture,
            position.x + origin.x,
            position.y + origin.y,
            origin.x, origin.y,
            dimension.x,
            dimension.y,
            scale.x,
            scale.y,
            rotation,
            regWaterOverlay.regionX,
            regWaterOverlay.regionY,
            regWaterOverlay.regionWidth,
            regWaterOverlay.regionHeight,
            false,
            false
        )
    }
}
