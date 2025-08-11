package cherry.gamebox.bunny.game.objects

import cherry.gamebox.bunny.game.Assets
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

class Mountains(private var length: Float) : AbstractGameObject() {
    private val regMountainLeft: TextureRegion
    private val regMountainRight: TextureRegion

    init {
        dimension.set(10f, 2f)
        regMountainLeft = Assets.levelDecoration.mountainLeft
        regMountainRight = Assets.levelDecoration.mountainRight

        origin.x = -dimension.x * 2
        length += dimension.x * 2
    }

    fun updateScrollPosition(camPosition: Vector2) {
        position[camPosition.x] = position.y
    }

    private fun drawMountain(batch: SpriteBatch, offsetX: Float, offsetY: Float, tintColor: Float) {
        var reg: TextureRegion
        batch.setColor(tintColor, tintColor, tintColor, 1f)
        var xRel = dimension.x * offsetX
        val yRel = dimension.y * offsetY
        var mountainLength = 0
        mountainLength += MathUtils.ceil(length / (2 * dimension.x))
        mountainLength += MathUtils.ceil(0.5f + offsetX)
        for (i in 0 until length.toInt()) {
            reg = regMountainLeft
            batch.draw(
                reg.texture,
                origin.x + xRel, position.y + origin.y + yRel,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation,
                reg.regionX, reg.regionY,
                reg.regionWidth, reg.regionHeight,
                false, false
            )
            xRel += dimension.x
            reg = regMountainRight
            batch.draw(
                reg.texture,
                origin.x + xRel, origin.y + yRel + position.y,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation,
                reg.regionX, reg.regionY,
                reg.regionWidth, reg.regionHeight,
                false, false
            )
            xRel += dimension.x
        }
        //reset Color to White
        batch.setColor(1f, 1f, 1f, 1f)
    }

    override fun render(batch: SpriteBatch) {
        drawMountain(batch, 0.5f, 0.5f, 0.5f)
        drawMountain(batch, 0.25f, 0.25f, 0.7f)
        drawMountain(batch, 0.05f, 0.0f, 0.9f)
    }

}
