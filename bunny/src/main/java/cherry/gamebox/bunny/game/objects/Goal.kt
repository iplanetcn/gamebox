package cherry.gamebox.bunny.game.objects

import cherry.gamebox.bunny.game.Assets
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion


class Goal : AbstractGameObject() {
    private var regGoal: TextureRegion

    init {
        dimension[3.0f] = 3.0f
        regGoal = Assets.levelDecoration.goal

        // Set bounding box for collision detection
        bounds[1f, Float.MIN_VALUE, 10f] = Float.MAX_VALUE
        origin[dimension.x / 2.0f] = 0.0f
    }

    override fun render(batch: SpriteBatch) {
        batch.draw(
            regGoal.texture,
            position.x - origin.x,
            position.y - origin.y,
            origin.x,
            origin.y,
            dimension.x,
            dimension.y,
            scale.x,
            scale.y,
            rotation,
            regGoal.regionX,
            regGoal.regionY,
            regGoal.regionWidth,
            regGoal.regionHeight,
            false,
            false
        )
    }
}