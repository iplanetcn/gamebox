package cherry.gamebox.superjumper.controller

import android.media.audiofx.DynamicsProcessing
import cherry.gamebox.core.GameLogger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerListener
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage

/**
 * GameControllerManager
 *
 * @author john
 * @since 2025-07-15
 */
class GameControllerManager(): ControllerListener {
    private var isPhysicalControllerConnected: Boolean = false
    var touchController: TouchController? = null

    init {
        // 初始化Controllers
        Controllers.addListener(this)
    }

    // 检查是否有物理控制器连接
    fun checkControllers() {
        isPhysicalControllerConnected = Controllers.getControllers().size > 0
    }

    // 只有在没有物理控制器连接时才渲染虚拟控制器
    fun render(batch: SpriteBatch) {
        if (!isPhysicalControllerConnected) {
            // 没有物理游戏控制器时，渲染虚拟控制器
            touchController?.apply {
                show()
            }
        }
    }

    fun update(delta: Float) {
        checkControllers()
        if (!isPhysicalControllerConnected) {
            // 没有物理游戏控制器时，更新虚拟控制器逻辑
            touchController?.update(delta)
        }
    }

    fun dispose() {
        Controllers.removeListener(this)
    }


    // 控制器连接事件
    override fun connected(controller: Controller) {
        isPhysicalControllerConnected = true
        controller.apply {
            GameLogger.debug("Physical controller connected: $name")
        }
    }

    // 控制器断开连接事件
    override fun disconnected(controller: Controller) {
        checkControllers()
        controller.apply {
            GameLogger.debug("Physical controller disconnected: $name")
        }
    }

    override fun buttonDown(
        controller: Controller,
        buttonCode: Int
    ): Boolean {

        return false
    }

    override fun buttonUp(
        controller: Controller,
        buttonCode: Int
    ): Boolean {
        return false
    }

    override fun axisMoved(
        controller: Controller,
        axisCode: Int,
        value: Float
    ): Boolean {
        return false
    }
}