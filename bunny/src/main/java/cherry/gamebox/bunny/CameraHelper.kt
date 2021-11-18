package cherry.gamebox.bunny

import com.badlogic.gdx.graphics.OrthographicCamera

import com.badlogic.gdx.math.MathUtils

import com.badlogic.gdx.math.Vector2




/**
 * CameraHelper
 *
 * @author john
 * @since 2021-11-19
 */
class CameraHelper {
    private val MAX_ZOOM_IN = 0.25f
    private val MAX_ZOOM_OUT = 10.0f
    private var target: AbstractGameObject? = null
    private val position: Vector2
    private var zoom: Float
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
        return hasTarget() && this.target == target // kiem tra xem co target va target co bang nhau ko
    }

    fun applyTo(camera: OrthographicCamera) {
        camera.position.x = position.x
        camera.position.y = position.y
        camera.zoom = zoom
        camera.update()
    }

    companion object {
        private val TAG = CameraHelper::class.java.name
    }

    init {
        position = Vector2()
        zoom = 1.0f
    }
}

