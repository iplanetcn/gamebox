package cherry.gamebox.superjumper.actors

import cherry.gamebox.superjumper.Assets
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Array

class CoinAnimationActor: Actor() {
    private val animation: Animation<Sprite>
    private var stateTime: Float = 0f // Track the elapsed time for the animation

    init {
        val keyFrames: Array<Sprite> = Array()
        for (sprite in Assets.sprites.coinList) {
            keyFrames.add(sprite)
        }
        animation = Animation<Sprite>(0.1f, keyFrames, Animation.PlayMode.LOOP)
    }

    override fun act(delta: Float) {
        super.act(delta)
        stateTime += delta
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        val currentFrame = animation.getKeyFrame(stateTime) // Get the current frame
        batch.draw(
            currentFrame,
            getX(),
            getY(),
            getWidth(),
            getHeight()
        )
    }
}