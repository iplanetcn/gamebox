package cherry.gamebox.puzzles.notcross
import com.badlogic.gdx.math.Vector2

/**
 * LineSegment
 *
 * @author john
 * @since 2021-12-02
 */
class LineSegment(var p: Vector2, var q: Vector2) {
    fun isIntersect(other: LineSegment) : Boolean {
        return LineSegmentsTool.isIntersect(this, other)
    }
}