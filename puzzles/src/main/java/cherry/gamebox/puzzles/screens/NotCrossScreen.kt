package cherry.gamebox.puzzles.screens

import cherry.gamebox.core.Assets
import cherry.gamebox.core.GameLogger
import cherry.gamebox.puzzles.PuzzlesGame
import cherry.gamebox.puzzles.notcross.LineSegment
import cherry.gamebox.puzzles.notcross.LineSegmentTools
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * NotCrossScreen
 *
 * @author john
 * @since 2021-12-02
 */

val OFFSET_X = SCREEN_WIDTH / 2
val OFFSET_Y = SCREEN_HEIGHT / 2
class NotCrossScreen(game: PuzzlesGame) : BaseScreen(game) {
    private var isTouched = false
    private var touchedPoint: Vector2? = null
    private val colorRed = Color(1f, 0f, 0f, 1f)
    private val colorGreen = Color(0f, 1f, 0f, 1f)

    private var points = arrayListOf(
        Vector2(OFFSET_X + 0f, OFFSET_Y + 0f),
        Vector2(OFFSET_X + 300f, OFFSET_Y + 300f),
        Vector2(OFFSET_X + 300f, OFFSET_Y + 0f),
        Vector2(OFFSET_X + 0f, OFFSET_Y + 300f)
    )

    private var l1 = LineSegment(p = points[0], q = points[1])
    private var l2 = LineSegment(p = points[2], q = points[3])

    init {
        touchedPoint = l1.p
    }

    override fun draw(delta: Float) {
        batcher.begin()
        batcher.draw(Assets.backgrounds.background, 0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT)
        batcher.end()

        Gdx.gl20.glLineWidth(10f)
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = getColor()
        shapeRenderer.line(l1.p, l1.q)
        shapeRenderer.line(l2.p, l2.q)
        shapeRenderer.end()

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = getColor()
        shapeRenderer.circle(l1.p.x, l1.p.y, 20f)
        shapeRenderer.circle(l1.q.x, l1.q.y, 20f)
        shapeRenderer.circle(l2.p.x, l2.p.y, 20f)
        shapeRenderer.circle(l2.q.x, l2.q.y, 20f)

        shapeRenderer.end()
    }

    override fun update(delta: Float) {
        // 判断是否正在拖拽, 并更新位置
        if (isTouched && touchedPoint != null) {
            touchedPoint?.apply {
                set(getInputPosition().x, getInputPosition().y)
                GameLogger.debug("update($this)")
            }
        }
    }

    /**
     * 根据是否相交,返回绿色或红色
     */
    private fun getColor() : Color{
        return if (LineSegmentTools.isIntersect(l1, l2)) colorRed else colorGreen
    }

    /**
     * 查找输入的点
     */
    private fun findTouchPoint(): Vector2? {
        val pos = getInputPosition()
        for (point in points) {
            if (calculateDistance(point, pos) < 50) {
                GameLogger.debug("findTouchPoint($point)")
                return point
            }
        }

        return null
    }

    /**
     * 计算两点之间的距离
     */
    private fun calculateDistance(p1: Vector2, p2: Vector2): Float {
        return sqrt((p1.x - p2.x).pow(2) + (p1.y - p2.y).pow(2))
    }

    /**
     * 获取输入位置
     */
    private fun getInputPosition(): Vector2 {
        val inputPos = Vector3()
        inputPos.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0F)
        camera.unproject(inputPos)
        return Vector2(inputPos.x, inputPos.y)
    }

    /**
     * 触摸屏幕
     */
    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        isTouched = true
        touchedPoint = findTouchPoint()
        GameLogger.debug("touchDown():$isTouched -> $touchedPoint")
        return true
    }

    /**
     * 离开屏幕
     */
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        isTouched = false
        touchedPoint = null
        GameLogger.debug("touchUp():$isTouched -> $touchedPoint")
        return true
    }

}