package cherry.gamebox.snake.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2

/**
 * Helper
 *
 * @author john
 * @since 2021-11-19
 */
object Helper {
    var debugRenderer = ShapeRenderer()

    fun drawDebugLine(
        start: Vector2,
        end: Vector2,
        lineWidth: Float,
        color: Color,
        projectionMatrix: Matrix4
    ) {
        Gdx.gl.glLineWidth(lineWidth)
        debugRenderer.projectionMatrix = projectionMatrix
        debugRenderer.begin(ShapeRenderer.ShapeType.Line)
        debugRenderer.color = color
        debugRenderer.line(start, end)
        debugRenderer.end()
        Gdx.gl.glLineWidth(1f)
    }

    fun drawDebugLine(start: Vector2, end: Vector2, projectionMatrix: Matrix4) {
        Gdx.gl.glLineWidth(50f)
        debugRenderer.projectionMatrix = projectionMatrix
        debugRenderer.begin(ShapeRenderer.ShapeType.Line)
        debugRenderer.color = Color.RED
        debugRenderer.line(start, end)
        debugRenderer.end()
        Gdx.gl.glLineWidth(50f)
    }
}