package cherry.gamebox.bunny.game.objects

import cherry.gamebox.bunny.game.Assets
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2


class Help : AbstractGameObject() {
    var collected = false

    var animNormal: Animation<TextureRegion>
    var animTilt: Animation<TextureRegion>
    var animTouch: Animation<TextureRegion>

    private val moveSrc = Vector2()
    private val moveDst = Vector2()
    private var moveTime = 0f
    private var timeMoveLeft = 0f
    private var moveEasing: Interpolation? = null

    private var visible = false
    private var oscillate = false

    init {
        dimension[3.5f] = 2.0f
        animNormal = Assets.book.animHelpIdle
        animTilt = Assets.book.animHelpTilt
        animTouch = Assets.book.animHelpTouch
        visible = false
        oscillate = false
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)
        if (timeMoveLeft > 0) {
            timeMoveLeft = 0f.coerceAtLeast(timeMoveLeft - deltaTime)
            val alpha = 1 - timeMoveLeft / moveTime
            position.x = moveSrc.x + moveDst.x * moveEasing!!.apply(alpha)
            position.y = moveSrc.y + moveDst.y * moveEasing!!.apply(alpha)
        } else if (visible) {
            if (!oscillate) {
                oscillate = true
                stateTime = 0f // MathUtils.random();
            }
            val oscX = 0f
            val oscY = 0f
            // oscX = MathUtils.cosDeg((stateTime * 10) % 360.0f * 25) * 0.05f;
// oscY = MathUtils.sinDeg((stateTime * 10) % 360.0f * 25) * 0.05f;
            position.x = moveSrc.x + moveDst.x + oscX
            position.y = moveSrc.y + moveDst.y + oscY
        }
    }

    override fun render(batch: SpriteBatch) {
        animation?.apply {
            val reg: TextureRegion = this.getKeyFrame(stateTime, true)
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
                false,
                false
            )
        }

    }

    fun moveBy(
        xSrc: Float,
        ySrc: Float,
        xDst: Float,
        yDst: Float,
        duration: Float,
        easing: Interpolation?
    ) {
        if (visible) return
        position.x = xSrc
        position.y = ySrc
        moveBy(xDst, yDst, duration, easing)
    }

    fun moveBy(x: Float, y: Float, duration: Float, easing: Interpolation?) {
        if (visible) return
        visible = true
        moveSrc.set(position)
        moveDst[x] = y
        moveTime = duration
        timeMoveLeft = duration
        moveEasing = easing
    }
}