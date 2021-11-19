package cherry.gamebox.bunny

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils

import com.badlogic.gdx.math.Vector2


class Clouds(
    val length: Float
) : AbstractGameObject() {
    var regClouds: MutableList<TextureRegion> = arrayListOf()
    var clouds: MutableList<Cloud> = arrayListOf()

    init {
        dimension.set(3.0f, 1.5f)
        regClouds.add(Assets.levelDecoration.cloud01)
        regClouds.add(Assets.levelDecoration.cloud02)
        regClouds.add(Assets.levelDecoration.cloud03)

        val distFac = 5f
        val numClouds = (length / distFac).toInt()

        IntRange(0, numClouds).map {
            val cloud = spawnCloud()
            cloud.position.x = it * distFac
            return@map cloud
        }.toCollection(clouds)
    }

    private fun spawnCloud(): Cloud {
        val cloud = Cloud()
        cloud.dimension.set(dimension)
        // select random cloud image
        cloud.setRegion(regClouds.random())
        // position
        val pos = Vector2()
        pos.x = length + 10 // position after end of level
        pos.y += 1.75f // base position
        // random additional position
        pos.y += (MathUtils.random(0.0f, 0.2f)
                * if (MathUtils.randomBoolean()) 1 else -1)
        cloud.position.set(pos)
        return cloud
    }

    override fun render(batch: SpriteBatch) {
        clouds.forEach { it.render(batch) }
    }

    class Cloud : AbstractGameObject() {
        var regCloud: TextureRegion? = null

        fun setRegion(region: TextureRegion) {
            regCloud = region
        }

        override fun render(batch: SpriteBatch) {
            val reg = regCloud
            reg?.apply {
                batch.draw(
                    texture, position.x + origin.x, position.y + position.y,
                    origin.x, origin.y, dimension.x, dimension.y,
                    scale.x, scale.y, rotation, regionX,
                    regionY, regionWidth,
                    regionHeight, false, false
                )
            }

        }

    }
}
