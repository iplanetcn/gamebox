package cherry.gamebox.bunny.game.objects

import cherry.gamebox.bunny.game.Assets
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion


/**
 * GoldCoin
 *
 * @author john
 * @since 2022-09-21
 */
class GoldCoin : AbstractGameObject() {
    private val regGoldCoin: TextureRegion
    var collected = false

    init {
        dimension[0.5f] = 0.5f
        regGoldCoin = Assets.goldCoin.goldCoin

        // Set bounding box for collision detection
        bounds[0f, 0f, dimension.x] = dimension.y
        collected = false
    }

    override fun render(batch: SpriteBatch) {
        if (collected) return
        batch.draw(
            regGoldCoin.texture,
            position.x,
            position.y,
            origin.x,
            origin.y,
            dimension.x,
            dimension.y,
            scale.x,
            scale.y,
            rotation,
            regGoldCoin.regionX,
            regGoldCoin.regionY,
            regGoldCoin.regionWidth,
            regGoldCoin.regionHeight,
            false,
            false
        )
    }

    fun getScore(): Int {
        return 100
    }
}