package cherry.gamebox.superjumper.controller

import cherry.gamebox.superjumper.Assets
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Disposable
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin


/**
 * VirtualController
 *
 * @author john
 * @since 2025-07-15
 */
class VirtualController(
    private val camera: OrthographicCamera
): Disposable {
    private var joystickBackground: Texture
    private var joystickKnob: Texture
    private var position: Vector2
    private var knobPosition: Vector2
    private val radius = 100f
    private var isTouched = false

    init {
        // 加载虚拟控制器资源
        joystickBackground = Assets.joystick.backgroundTexture
        joystickKnob = Assets.joystick.knobTexture
        // 虚拟控制器位置
        position = Vector2(200f, 200f)
        knobPosition = Vector2(position)
    }

    fun render(batch: SpriteBatch) {
        // 绘制虚拟控制器
        batch.draw(joystickBackground, position.x - radius, position.y - radius, radius * 2, radius * 2);
        batch.draw(joystickKnob, knobPosition.x - joystickKnob.width /2, knobPosition.y - joystickKnob.height /2);
    }

    fun update(delta: Float) {
        // 处理触摸输入
        if (Gdx.input.isTouched) {
            // 将屏幕坐标转换为相机坐标
            var touchPos3D = Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            touchPos3D = camera.unproject(touchPos3D)
            val touchPos = Vector2(touchPos3D.x, touchPos3D.y)

            // 检查是否触摸在操纵杆范围内(扩大触摸范围以提高灵敏度)
            if (touchPos.dst(position) < radius * 3) {
                isTouched = true
                val direction = touchPos.cpy().sub(position)

                // 使用normalize处理方向向量
                if (direction.len() > radius) {
                    // 归一化后缩放到最大半径
                   direction.nor().scl(radius)
                }
                knobPosition.set(touchPos)


                // 限制操纵杆移动范围
                if (knobPosition.dst(position) > radius) {
                    val angle = atan2(
                        (touchPos.y - position.y).toDouble(),
                        (touchPos.x - position.x).toDouble()
                    ).toFloat()
                    knobPosition.set(
                        position.x + cos(angle.toDouble()).toFloat() * radius,
                        position.y + sin(angle.toDouble()).toFloat() * radius
                    )
                }
            } else {
                isTouched = false
                knobPosition.set(position)
            }
        } else {
            isTouched = false
            knobPosition.set(position)
        }
    }

    override fun dispose() {
        joystickBackground.dispose()
        joystickKnob.dispose()
    }

    // 获取操纵杆方向
    fun getDirection(): Vector2 {
        if (!isTouched) return Vector2.Zero
        return Vector2(
            (knobPosition.x - position.x) / radius,
            (knobPosition.y - position.y) / radius
        )
    }

}