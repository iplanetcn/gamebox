package cherry.gamebox.bunny.game.objects

import cherry.gamebox.bunny.game.Assets
import cherry.gamebox.bunny.util.CharacterSkin
import cherry.gamebox.bunny.util.Constants
import cherry.gamebox.bunny.util.GamePreferences
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion


/**
 * BunnyHead
 *
 * @author john
 * @since 2022-09-21
 */
private const val JUMP_TIME_MAX = 0.3f
private const val JUMP_TIME_MIN = 0.1f
private const val JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f

class BunnyHead : AbstractGameObject() {
    enum class ViewDirection {
        LEFT, RIGHT
    }

    enum class JumpState {
        GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING
    }

    private var regHead: TextureRegion
    var viewDirection: ViewDirection
    var timeJumping = 0f
    var jumpState: JumpState
    var hasFeatherPowerup = false
    var timeLeftFeatherPowerup = 0f

    init {
        dimension[1f] = 1f
        regHead = Assets.bunny.head

        // Center image on game object
        origin[dimension.x / 2] = dimension.y / 2

        // Bounding box for collision detection
        bounds[0f, 0f, dimension.x] = dimension.y

        // Set physics values
        terminalVelocity[3.0f] = 4.0f
        friction[12.0f] = 0.0f
        acceleration[0.0f] = -25.0f

        // View direction
        viewDirection = ViewDirection.RIGHT

        // Jump state
        jumpState = JumpState.FALLING
        timeJumping = 0f

        // Power-ups
        hasFeatherPowerup = false
        timeLeftFeatherPowerup = 0f
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)
        if (velocity.x != 0f) {
            viewDirection = if (velocity.x < 0) ViewDirection.LEFT else ViewDirection.RIGHT
        }
        if (timeLeftFeatherPowerup > 0) {
            timeLeftFeatherPowerup -= deltaTime
            if (timeLeftFeatherPowerup < 0) {
                // disable power-up
                timeLeftFeatherPowerup = 0f
                setFeatherPowerup(false)
            }
        }
    }

    override fun updateMotionY(deltaTime: Float) {
        when (jumpState) {
            JumpState.GROUNDED -> jumpState = JumpState.FALLING
            JumpState.JUMP_RISING -> {
                // Keep track of jump time
                timeJumping += deltaTime
                // Jump time left?
                if (timeJumping <= JUMP_TIME_MAX) {
                    // Still jumping
                    velocity.y = terminalVelocity.y
                }
            }
            JumpState.FALLING -> {}
            JumpState.JUMP_FALLING -> {
                // Add delta times to track jump time
                timeJumping += deltaTime
                // Jump to minimal height if jump key was pressed too short
                if (timeJumping > 0 && timeJumping <= JUMP_TIME_MIN) {
                    // Still jumping
                    velocity.y = terminalVelocity.y
                }
            }
        }
        super.updateMotionY(deltaTime)
    }

    override fun render(batch: SpriteBatch) {
        var reg: TextureRegion?

        // Apply Skin Color
        batch.color = CharacterSkin.values()[GamePreferences.charSkin].color

        // Set special color when game object has a feather power-up
        if (hasFeatherPowerup) {
            batch.setColor(1.0f, 0.8f, 0.0f, 1.0f)
        }

        // Draw image
        reg = regHead
        batch.draw(
            reg.texture,
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
            viewDirection == ViewDirection.LEFT,
            false
        )

        // Reset color to white
        batch.setColor(1f, 1f, 1f, 1f)
    }

    fun setFeatherPowerup(pickedUp: Boolean) {
        hasFeatherPowerup = pickedUp
        if (pickedUp) {
            timeLeftFeatherPowerup = Constants.ITEM_FEATHER_POWERUP_DURATION
        }
    }

    fun hasFeatherPowerup(): Boolean {
        return hasFeatherPowerup && timeLeftFeatherPowerup > 0
    }

    fun setJumping(jumpKeyPressed: Boolean) {
        when (jumpState) {
            JumpState.GROUNDED -> if (jumpKeyPressed) {
                // Start counting jump time from the beginning
                timeJumping = 0f
                jumpState = JumpState.JUMP_RISING
            }
            JumpState.JUMP_RISING -> if (!jumpKeyPressed) {
                jumpState = JumpState.JUMP_FALLING
            }
            JumpState.FALLING, JumpState.JUMP_FALLING -> if (jumpKeyPressed && hasFeatherPowerup) {
                timeJumping = JUMP_TIME_OFFSET_FLYING
                jumpState = JumpState.JUMP_RISING
            }
        }
    }
}