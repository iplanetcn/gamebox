package cherry.gamebox.bunny.game.objects

import cherry.gamebox.bunny.game.Assets
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Rock(private var length : Int = 1): AbstractGameObject(){
    private val regEdge: TextureRegion
    private val middleEdge: TextureRegion

    init {
        dimension.set(1f, 1.5f)
        regEdge = Assets.instance.rock.edge
        middleEdge = Assets.instance.rock.middle

    }

    fun increaseLength(amount: Int ) {
        this.length += amount
    }

    override fun render(batch: SpriteBatch) {
        var relX = 0f
        val relY = 0f
        var reg: TextureRegion = regEdge
        relX -= dimension.x / 4
        batch.draw(
            reg.texture,
            position.x + relX, position.y + relY,
            origin.x, origin.y,
            dimension.x / 4, dimension.y,
            scale.x, scale.y,
            rotation,
            reg.regionX,
            reg.regionY,
            reg.regionWidth,
            reg.regionHeight,
            false,
            false
        )
        relX = 0f
        reg = middleEdge


        for (i in 0 until length) {
            batch.draw(
                reg.texture,
                position.x + relX, position.y + relY,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation,
                reg.regionX, reg.regionY,
                reg.regionWidth, reg.regionHeight,
                false, false
            )
            relX += dimension.x
        }

        reg = regEdge
        batch.draw(
            reg.texture,
            position.x + relX, position.y + relY,
            origin.x + dimension.x / 8, origin.y,
            dimension.x / 4, dimension.y,
            scale.x, scale.y,
            rotation,
            reg.regionX, reg.regionY,
            reg.regionWidth, reg.regionHeight,
            true, false
        )

    }

}
