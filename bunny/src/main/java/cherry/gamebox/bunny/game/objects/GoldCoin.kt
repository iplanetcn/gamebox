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
    private var regGoldCoin: TextureRegion? = null
    var collected = false

    init {
        init()
    }

    private fun init() {
        dimension[0.5f] = 0.5f
        regGoldCoin = Assets.goldCoin.goldCoin

        // Set bounding box for collision detection
        bounds[0f, 0f, dimension.x] = dimension.y
        collected = false
    }

    override fun render(batch: SpriteBatch) {
        if (collected) return
        var reg: TextureRegion? = null
        reg = regGoldCoin
        batch.draw(
            reg!!.texture,
            position.x,
            position.y,
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

    fun getScore(): Int {
        return 100
    }
}