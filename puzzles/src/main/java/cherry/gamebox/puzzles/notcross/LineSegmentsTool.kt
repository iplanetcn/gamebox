package cherry.gamebox.puzzles.notcross

import android.graphics.Point
import kotlin.math.max
import kotlin.math.min

/**
 * LineSegmentsTool
 *
 * @author john
 * @since 2021-12-02
 */
object LineSegmentsTool {
    private fun onSegment(p: Point, q: Point, r: Point): Boolean {
        if (q.x <= max(p.x, r.x) && q.x >= min(p.x, r.x) &&
            q.y <= max(p.y, r.y) && q.y >= min(p.y, r.y)
        ) {
            return true
        }

        return false
    }

    // 0 --> p,q and r are collinear
    // 1 --> Clockwise
    // 2 --> CounterClockwise
    private fun orientation(p: Point, q: Point, r: Point): Int {
        val temp = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y)

        return when {
            temp > 0 -> 1
            temp < 0 -> 2
            else -> 0
        }
    }

    fun isIntersect(l1: LineSegment, l2: LineSegment) : Boolean {
        val o1 = orientation(l1.p, l1.q, l2.p)
        val o2 = orientation(l1.p, l1.q, l2.q)
        val o3 = orientation(l2.p, l2.q, l1.p)
        val o4 = orientation(l2.p, l2.q, l1.q)

        if (o1 != o2 && o3 != o4) {
            return true
        }

        if (o1 == 0 && onSegment(l1.p, l2.p, l1.q)) {
            return true
        }

        if (o2 == 0 && onSegment(l1.p, l2.q, l1.q)) {
            return true
        }

        if (o3 == 0 && onSegment(l2.p, l1.p, l2.q)) {
            return true
        }

        if (o4 == 0 && onSegment(l2.p, l1.q, l2.q)) {
            return true
        }


        return false
    }

}