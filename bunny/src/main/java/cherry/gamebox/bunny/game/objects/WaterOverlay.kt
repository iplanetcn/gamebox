package cherry.gamebox.bunny.game.objects

import cherry.gamebox.bunny.game.Assets
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion


class WaterOverlay(private val length: Float) : AbstractGameObject() {
    private var regWaterOverlay: TextureRegion? = null

    init {
        dimension[length * 10] = 3f
        regWaterOverlay = Assets.levelDecoration.waterOverlay
        origin.x = -dimension.x / 2
    }

    override fun render(batch: SpriteBatch) {
        var reg: TextureRegion? = null
        reg = regWaterOverlay
        batch.draw(
            reg!!.texture,
            position.x + origin.x,
            position.y + origin.y,
            origin.x, origin.y,
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
