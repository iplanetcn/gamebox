package cherry.gamebox.superjumper.controller

import cherry.gamebox.core.GameLogger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerListener
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
/**
 * GameControllerManager
 *
 * @author john
 * @since 2025-07-15
 */
class GameControllerManager(
    private val camera: OrthographicCamera
): ControllerListener {
    private var isPhysicalControllerConnected: Boolean = false
    private var virtualController: VirtualController
    private var hudCamera: OrthographicCamera

    init {
        // 初始化Controllers
        Controllers.addListener(this)

        // 初始化虚拟控制器
        virtualController = VirtualController(camera)


        this.hudCamera = OrthographicCamera()
        hudCamera.setToOrtho(
            false,
            Gdx.graphics.width.toFloat(),
            Gdx.graphics.height.toFloat()
        )
    }

    // 检查是否有物理控制器连接
    fun checkControllers() {
        isPhysicalControllerConnected = Controllers.getControllers().size > 0
    }

    // 只有在没有物理控制器连接时才渲染虚拟控制器
    fun render(batch: SpriteBatch) {
        if (!isPhysicalControllerConnected) {
            batch.projectionMatrix = hudCamera.combined
            virtualController.render(batch)
        }
    }

    fun update(delta: Float) {
        checkControllers()
        if (!isPhysicalControllerConnected) {
            virtualController.update(delta)
        }
    }

    fun dispose() {
        virtualController.dispose()
        Controllers.removeListener(this)
    }


    // 控制器连接事件
    override fun connected(controller: Controller?) {
        isPhysicalControllerConnected = true
        controller?.apply {
            GameLogger.debug("Physical controller connected: $name")
        }
    }

    // 控制器断开连接事件
    override fun disconnected(controller: Controller?) {
        checkControllers()
        controller?.apply {
            GameLogger.debug("Physical controller disconnected: $name")
        }
    }

    override fun buttonDown(
        controller: Controller?,
        buttonCode: Int
    ): Boolean {
        return false
    }

    override fun buttonUp(
        controller: Controller?,
        buttonCode: Int
    ): Boolean {
        return false
    }

    override fun axisMoved(
        controller: Controller?,
        axisCode: Int,
        value: Float
    ): Boolean {
        return false
    }
}