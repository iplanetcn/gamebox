package cherry.gamebox.bunny.game.objects

import cherry.gamebox.bunny.game.Assets
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

class Clouds(private val length: Float) : AbstractGameObject() {
    private var regClouds: MutableList<TextureRegion> = mutableListOf()
    private var clouds: MutableList<Cloud> = mutableListOf()


    init {
        dimension[3.0f] = 1.5f
        regClouds.add(Assets.instance.levelDecoration.cloud01)
        regClouds.add(Assets.instance.levelDecoration.cloud02)
        regClouds.add(Assets.instance.levelDecoration.cloud03)
        val distFac = 5
        val numClouds = (length / distFac).toInt()
        for (i in 0 until numClouds) {
            val cloud = spawnCloud()
            cloud.position.x = (i * distFac).toFloat()
            clouds.add(cloud)
        }
    }

    private fun spawnCloud(): Cloud {
        val cloud = Cloud()
        cloud.dimension.set(dimension)

        // select random cloud image
        cloud.setRegion(regClouds.random())

        // position
        val pos = Vector2()
        pos.x = length + 10 // position after end of level
        pos.y += 1.75.toFloat() // base position
        pos.y += MathUtils.random(
            0.0f,
            0.2f
        ) * if (MathUtils.randomBoolean()) 1 else -1 // random additional position
        cloud.position.set(pos)
        return cloud
    }

    override fun render(batch: SpriteBatch) {
        for (cloud in clouds) cloud.render(batch)
    }


    private inner class Cloud : AbstractGameObject() {
        private var regCloud: TextureRegion? = null
        fun setRegion(region: TextureRegion?) {
            regCloud = region
        }

        override fun render(batch: SpriteBatch) {
            val reg = regCloud
            reg?.apply {
                batch.draw(
                    reg.texture,
                    position.x + origin.x,
                    position.y + origin.y,
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
    }
}