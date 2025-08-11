package cherry.gamebox.superjumper.actors


import cherry.gamebox.superjumper.Assets
import cherry.gamebox.superjumper.controller.TouchController
import cherry.gamebox.superjumper.extension.compareTo
import cherry.gamebox.superjumper.fsm.PlayerState
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Array


class PlayerAnimationActor(
    private val touchController: TouchController
) : Actor() {
    private var state: PlayerState = PlayerState.IDLE
    private val animationIdle: Animation<Sprite>
    private val animationRun: Animation<Sprite>
    private val animationRoll: Animation<Sprite>
    private val animationHit: Animation<Sprite>
    private val animationDeath: Animation<Sprite>
    private var stateTime: Float = 0f // Track the elapsed time for the animation

    private val speed: Float = 100f
    private var velocity = Vector2()
    private var currentFrame: TextureRegion

    init {
        val keyFramesIdle: Array<Sprite> = Array()
        Assets.sprites.knightIdleList.forEach { item -> keyFramesIdle.add(item) }
        val keyFramesRun: Array<Sprite> = Array()
        Assets.sprites.knightRunList.forEach { item -> keyFramesRun.add(item) }
        val keyFramesRoll: Array<Sprite> = Array()
        Assets.sprites.knightRollList.forEach { item -> keyFramesRoll.add(item) }
        val keyFramesHit: Array<Sprite> = Array()
        Assets.sprites.knightHitList.forEach { item -> keyFramesHit.add(item) }
        val keyFramesDeath: Array<Sprite> = Array()
        Assets.sprites.knightDeathList.forEach { item -> keyFramesDeath.add(item) }

        animationIdle = Animation<Sprite>(0.1f, keyFramesIdle, Animation.PlayMode.LOOP)
        animationRun = Animation<Sprite>(0.05f, keyFramesRun, Animation.PlayMode.LOOP)
        animationRoll = Animation<Sprite>(0.05f, keyFramesRoll, Animation.PlayMode.NORMAL)
        animationHit = Animation<Sprite>(0.1f, keyFramesHit, Animation.PlayMode.NORMAL)
        animationDeath = Animation<Sprite>(0.2f, keyFramesDeath, Animation.PlayMode.NORMAL)

        currentFrame = animationIdle.keyFrames.first()

        touchController.hitButton.addListener {
            hit()
            false
        }
    }

    override fun act(delta: Float) {
        super.act(delta)
        stateTime += delta

        when (state) {
            PlayerState.IDLE -> {
                if (touchController.getLeftPadKnobPercent() > Vector2.Zero) {
                    velocity = Vector2(
                        touchController.getLeftPadKnobPercent().x * speed,
                        touchController.getLeftPadKnobPercent().y * speed
                    )
                    changeState(PlayerState.RUN)
                }
            }

            PlayerState.RUN -> {
                if (touchController.getLeftPadKnobPercent() == Vector2.Zero) {
                    velocity = Vector2.Zero
                    changeState(PlayerState.IDLE)
                }
            }

            PlayerState.ROLL -> if (animationRoll.isAnimationFinished(stateTime)) {
                changeState(PlayerState.IDLE)
            }

            PlayerState.HIT -> if (animationHit.isAnimationFinished(stateTime)) {
                changeState(PlayerState.IDLE)
            }

            else -> {}
        }



        currentFrame = getAnimationByState().getKeyFrame(stateTime)
        currentFrame.flip(velocity.x <= 0, false)

        x += velocity.x * delta
        y += velocity.y * delta
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight())
    }

    private fun getAnimationByState(): Animation<Sprite> {
        return when (state) {
            PlayerState.IDLE -> animationIdle
            PlayerState.RUN -> animationRun
            PlayerState.ROLL -> animationRoll
            PlayerState.HIT -> animationHit
            PlayerState.DEATH -> animationDeath
        }
    }

    fun changeState(newState: PlayerState) {
        if (state == PlayerState.DEATH) return; // 死亡后不能再变

        state = newState
        stateTime = 0f

        if (newState == PlayerState.ROLL) {
            velocity.x = 5f // 触发 roll 加速
        } else if (newState == PlayerState.HIT) {
            velocity.x = 0f
        } else if (newState == PlayerState.DEATH) {
            velocity.x = 0f
        }
    }

    fun hit() {
        changeState(PlayerState.HIT)
    }

    fun death() {
        changeState(PlayerState.DEATH)
    }
}