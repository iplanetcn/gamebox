package cherry.gamebox.bunny.game.objects

import cherry.gamebox.bunny.game.Assets
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils


private const val FLOAT_CYCLE_TIME = 2.0f
private const val FLOAT_AMPLITUDE = 0.25f
class Rock(private var length : Int = 1): AbstractGameObject(){

    private var regEdge: TextureRegion
    private var regMiddle: TextureRegion

    private var floatCycleTimeLeft = 0f
    private var floatingDownwards = false

    init {
        dimension[1f] = 1.5f
        regEdge = Assets.rock.edge
        regMiddle = Assets.rock.middle

        // Start length of this rock
        setLength(1)
        floatingDownwards = false
        floatCycleTimeLeft = MathUtils.random(0f, FLOAT_CYCLE_TIME / 2)
    }

    fun setLength(length: Int) {
        this.length = length
        // Update bounding box for collision detection
        bounds[0f, 0f, dimension.x * length] = dimension.y
    }

    fun increaseLength(amount: Int) {
        setLength(length + amount)
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)
        floatCycleTimeLeft -= deltaTime
        if (floatCycleTimeLeft <= 0) {
            floatCycleTimeLeft = FLOAT_CYCLE_TIME
            floatingDownwards = !floatingDownwards
            body!!.setLinearVelocity(0f, FLOAT_AMPLITUDE * if (floatingDownwards) -1 else 1)
        } else {
            body!!.linearVelocity = body!!.linearVelocity.scl(0.98f)
        }
    }

    override fun render(batch: SpriteBatch) {
        var relX = 0f
        val relY = 0f

        // Draw left edge
        var reg: TextureRegion = regEdge
        relX -= dimension.x / 4
        batch.draw(
            reg.texture,
            position.x + relX,
            position.y + relY,
            origin.x,
            origin.y,
            dimension.x / 4,
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

        // Draw middle
        relX = 0f
        reg = regMiddle
        repeat(length) {
            batch.draw(
                reg.texture,
                position.x + relX,
                position.y + relY,
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
            relX += dimension.x
        }

        // Draw right edge
        reg = regEdge
        batch.draw(
            reg.texture,
            position.x + relX,
            position.y + relY,
            origin.x + dimension.x / 8,
            origin.y,
            dimension.x / 4,
            dimension.y,
            scale.x,
            scale.y,
            rotation,
            reg.regionX,
            reg.regionY,
            reg.regionWidth,
            reg.regionHeight,
            true,
            false
        )
    }

}
