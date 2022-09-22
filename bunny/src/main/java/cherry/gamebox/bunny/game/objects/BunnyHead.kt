package cherry.gamebox.bunny.game.objects

import cherry.gamebox.bunny.game.Assets
import cherry.gamebox.bunny.util.AudioManager
import cherry.gamebox.bunny.util.CharacterSkin
import cherry.gamebox.bunny.util.Constants
import cherry.gamebox.bunny.util.GamePreferences
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils


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

    private var animNormal: Animation<TextureRegion>
    private var animCopterTransform: Animation<TextureRegion>
    private var animCopterTransformBack: Animation<TextureRegion>
    private var animCopterRotate: Animation<TextureRegion>

    var viewDirection: ViewDirection? = null

    var jumpState: JumpState
    var timeJumping = 0f

    var hasFeatherPowerup = false
    var timeLeftFeatherPowerup = 0f

    var dustParticles: ParticleEffect = ParticleEffect()

    init {
        dimension[1f] = 1f
        animNormal = Assets.bunny.animNormal
        animCopterTransform = Assets.bunny.animCopterTransform
        animCopterTransformBack = Assets.bunny.animCopterTransformBack
        animCopterRotate = Assets.bunny.animCopterRotate
        animation = animNormal

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

        // Particles
        dustParticles.load(
            Gdx.files.internal("particles/dust.pfx"),
            Gdx.files.internal("particles")
        )
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)
        if (velocity.x != 0f) {
            viewDirection = if (velocity.x < 0) ViewDirection.LEFT else ViewDirection.RIGHT
        }
        if (timeLeftFeatherPowerup > 0) {
            if (animation === animCopterTransformBack) {
                // Restart "Transform" animation if another feather power-up
                // was picked up during "TransformBack" animation. Otherwise,
                // the "TransformBack" animation would be stuck while the
                // power-up is still active.
                animation = animCopterTransform
            }
            timeLeftFeatherPowerup -= deltaTime
            if (timeLeftFeatherPowerup < 0) {
                // disable power-up
                timeLeftFeatherPowerup = 0f
                setFeatherPowerup(false)
                animation = animCopterTransformBack
            }
        }
        dustParticles.update(deltaTime)

        // Change animation state according to feather power-up
        if (hasFeatherPowerup) {
            if (animation === animNormal) {
                animation = animCopterTransform
            } else if (animation === animCopterTransform) {
                if (animation!!.isAnimationFinished(stateTime)) animation = animCopterRotate
            }
        } else {
            if (animation === animCopterRotate) {
                if (animation!!.isAnimationFinished(stateTime)) animation = animCopterTransformBack
            } else if (animation === animCopterTransformBack) {
                if (animation!!.isAnimationFinished(stateTime)) animation = animNormal
            }
        }
    }

    override fun updateMotionY(deltaTime: Float) {
        when (jumpState) {
            JumpState.GROUNDED -> {
                jumpState = JumpState.FALLING
                if (velocity.x != 0f) {
                    dustParticles.setPosition(position.x + dimension.x / 2, position.y)
                    dustParticles.start()
                }
            }
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
        if (jumpState != JumpState.GROUNDED) {
            dustParticles.allowCompletion()
            super.updateMotionY(deltaTime)
        }
    }

    override fun render(batch: SpriteBatch) {

        // Draw Particles
        dustParticles.draw(batch)

        // Apply Skin Color
        batch.color = CharacterSkin.values()[GamePreferences.charSkin].color
        var dimCorrectionX = 0f
        var dimCorrectionY = 0f
        if (animation !== animNormal) {
            dimCorrectionX = 0.05f
            dimCorrectionY = 0.2f
        }

        // Draw image
        val reg: TextureRegion = animation!!.getKeyFrame(stateTime, true)
        batch.draw(
            reg.texture,
            position.x,
            position.y,
            origin.x,
            origin.y,
            dimension.x + dimCorrectionX,
            dimension.y
                    + dimCorrectionY,
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
                AudioManager.play(Assets.sounds.jump)
                // Start counting jump time from the beginning
                timeJumping = 0f
                jumpState = JumpState.JUMP_RISING
            }
            JumpState.JUMP_RISING -> if (!jumpKeyPressed) {
                jumpState = JumpState.JUMP_FALLING
            }
            JumpState.FALLING, JumpState.JUMP_FALLING -> if (jumpKeyPressed && hasFeatherPowerup) {
                AudioManager.play(
                    Assets.sounds.jumpWithFeather,
                    1f,
                    MathUtils.random(1.0f, 1.1f)
                )
                timeJumping = JUMP_TIME_OFFSET_FLYING
                jumpState = JumpState.JUMP_RISING
            }
        }
    }
}