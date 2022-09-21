package cherry.gamebox.bunny.game.objects

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body


abstract class AbstractGameObject {

    var position: Vector2 = Vector2()
    var dimension: Vector2 = Vector2(1f, 1f)
    var origin: Vector2 = Vector2()
    var scale: Vector2 = Vector2(1f, 1f)
    var rotation: Float = 0f
    var velocity: Vector2 = Vector2()
    var terminalVelocity: Vector2 = Vector2(1f, 1f)
    var friction: Vector2 = Vector2()
    var acceleration: Vector2 = Vector2()
    var bounds: Rectangle = Rectangle()
    var body: Body? = null
    var stateTime: Float = 0f
    var animation: Animation<TextureRegion>? = null

    open fun update(deltaTime: Float) {
        stateTime += deltaTime
        if (body == null) {
            updateMotionX(deltaTime)
            updateMotionY(deltaTime)

            // Move to new position
            position.x += velocity.x * deltaTime
            position.y += velocity.y * deltaTime
        } else {
            position.set(body!!.position)
            rotation = body!!.angle * MathUtils.radiansToDegrees
        }
    }

    protected open fun updateMotionX(deltaTime: Float) {
        if (velocity.x != 0f) {
            // Apply friction
            if (velocity.x > 0) {
                velocity.x = (velocity.x - friction.x * deltaTime).coerceAtLeast(0f)
            } else {
                velocity.x = (velocity.x + friction.x * deltaTime).coerceAtMost(0f)
            }
        }
        // Apply acceleration
        velocity.x += acceleration.x * deltaTime
        // Make sure the object's velocity does not exceed the
        // positive or negative terminal velocity
        velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x)
    }

    protected open fun updateMotionY(deltaTime: Float) {
        if (velocity.y != 0f) {
            // Apply friction
            if (velocity.y > 0) {
                velocity.y = (velocity.y - friction.y * deltaTime).coerceAtLeast(0f)
            } else {
                velocity.y = (velocity.y + friction.y * deltaTime).coerceAtMost(0f)
            }
        }
        // Apply acceleration
        velocity.y += acceleration.y * deltaTime
        // Make sure the object's velocity does not exceed the
        // positive or negative terminal velocity
        velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y, terminalVelocity.y)
    }

    open fun setTheAnimation(animation: Animation<TextureRegion>?) {
        this.animation = animation
        stateTime = 0f
    }

    abstract fun render(batch: SpriteBatch)
}