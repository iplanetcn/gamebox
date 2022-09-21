package cherry.gamebox.bunny.util

import cherry.gamebox.bunny.game.objects.AbstractGameObject
import com.badlogic.gdx.graphics.OrthographicCamera

import com.badlogic.gdx.math.MathUtils

import com.badlogic.gdx.math.Vector2

/**
 * CameraHelper
 *
 * @author john
 * @since 2021-11-19
 */
private const val MAX_ZOOM_IN = 0.25f
private const val MAX_ZOOM_OUT = 10.0f

class CameraHelper {
    private var target: AbstractGameObject? = null
    private val position: Vector2 = Vector2()
    private var zoom: Float = 1.0f

    fun update(deltaTime: Float) {
        if (!hasTarget()) return
        position.x = target!!.position.x + target!!.position.y
        position.y = target!!.position.y + target!!.position.y
    }

    fun setTarget(target: AbstractGameObject?) {
        this.target = target
    }

    fun getTarget(): AbstractGameObject? {
        return target
    }

    fun setPosition(x: Float, y: Float) {
        position[x] = y
    }

    fun getPosition(): Vector2 {
        return position
    }

    fun addZoom(amount: Float) {
        setZoom(zoom + amount)
    }

    fun setZoom(zoom: Float) {
        this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT)
    }

    fun getZoom(): Float {
        return zoom
    }

    fun getTaget(): AbstractGameObject? {
        return target
    }

    fun hasTarget(): Boolean {
        return target != null
    }

    fun hasTarget(target: AbstractGameObject): Boolean {
        return hasTarget() && this.target == target
    }

    fun applyTo(camera: OrthographicCamera) {
        camera.position.x = position.x
        camera.position.y = position.y
        camera.zoom = zoom
        camera.update()
    }
}

