package cherry.gamebox.puzzles.notcross

import android.graphics.Point

/**
 * LineSegment
 *
 * @author john
 * @since 2021-12-02
 */
class LineSegment(var p: Point, var q: Point) {

    fun isIntersect(other: LineSegment) : Boolean {
        return LineSegmentsTool.isIntersect(this, other)
    }
}