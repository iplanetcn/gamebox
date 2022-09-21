package cherry.gamebox.link

import java.util.*

/**
 * LinkSearch
 *
 * @author john
 * @since 2020-12-25
 */
object LinkSearch {
    private fun matchBlock(
        data: ArrayList<out ArrayList<out LinkInterface>>,
        srcPt: Point,
        destPt: Point
    ): Boolean {
        // 如果不属于0折连接则返回false
        if (srcPt.x != destPt.x && srcPt.y != destPt.y) {
            return false
        }

        val min: Int
        val max: Int

        if (srcPt.x == destPt.x) {
            // 如果两点的x坐标相等，则在水平方向上扫描
            min = if (srcPt.y < destPt.y) srcPt.y else destPt.y
            max = if (srcPt.y > destPt.y) srcPt.y else destPt.y
            for (y in min + 1 until max) {
                if (!data[srcPt.x][y].isEmpty()) {
                    return false
                }
            }
        } else {
            // 如果两点的y坐标相等，则在竖直方向上扫描
            min = if (srcPt.x < destPt.x) srcPt.x else destPt.x
            max = if (srcPt.x > destPt.x) srcPt.x else destPt.x
            for (x in min + 1 until max) {
                if (!data[x][srcPt.y].isEmpty()) {
                    return false
                }
            }
        }

        return true
    }

    private fun matchBlockOne(
        data: ArrayList<out ArrayList<out LinkInterface>>,
        srcPt: Point,
        destPt: Point
    ): Point? {
        // 如果不属于1折连接则返回null
        if (srcPt.x == destPt.x || srcPt.y == destPt.y) {
            return null
        }

        // 测试对角点1
        val pt1 = Point(srcPt.x, destPt.y)
        if (data[pt1.x][pt1.y].isEmpty()) {
            val stMatch = matchBlock(data, srcPt, pt1)
            val tdMatch = if (stMatch) matchBlock(data, pt1, destPt) else false
            if (stMatch && tdMatch) {
                return pt1
            }
        }

        // 测试对角点2
        val pt2 = Point(destPt.x, srcPt.y)
        if (data[pt2.x][pt2.y].isEmpty()) {
            val stMatch = matchBlock(data, srcPt, pt2)
            val tdMatch = if (stMatch) matchBlock(data, pt2, destPt) else false
            if (stMatch && tdMatch) {
                return pt2
            }
        }

        return null
    }

    fun matchBlockTwo(
        data: ArrayList<out ArrayList<out LinkInterface>>,
        srcPt: Point,
        destPt: Point
    ): List<Point>? {
        if (data.isEmpty()) return null
        if (srcPt.x < 0 || srcPt.x > data.size) return null
        if (srcPt.y < 0 || srcPt.y > data[0].size) return null
        if (destPt.x < 0 || destPt.x > data.size) return null
        if (destPt.y < 0 || destPt.y > data[0].size) return null

        if (matchBlock(data, srcPt, destPt)) {
            return LinkedList<Point>()
        }

        val list = LinkedList<Point>()
        val point = matchBlockOne(data, srcPt, destPt)

        if (point != null) {
            list.add(point)
            return list
        }

        for (i in srcPt.y + 1 until data[srcPt.x].size) {
            if (data[srcPt.x][i].isEmpty()) {
                val src = Point(srcPt.x, i)
                val dest = matchBlockOne(data, src, destPt)
                if (dest != null) {
                    list.add(src)
                    list.add(dest)
                    return list
                }
            } else {
                break
            }
        }

        for (i in srcPt.y - 1 downTo 1) {
            if (data[srcPt.x][i].isEmpty()) {
                val src = Point(srcPt.x, i)
                val dest = matchBlockOne(data, src, destPt)
                if (dest != null) {
                    list.add(src)
                    list.add(dest)
                    return list
                }
            } else {
                break
            }
        }


        for (i in srcPt.x + 1 until data.size) {
            if (data[i][srcPt.y].isEmpty()) {
                val src = Point(i, srcPt.y)
                val dest = matchBlockOne(data, src, destPt)
                if (dest != null) {
                    list.add(src)
                    list.add(dest)
                    return list
                }
            } else {
                break
            }
        }

        for (i in srcPt.x - 1 downTo 1) {
            if (data[i][srcPt.y].isEmpty()) {
                val src = Point(i, srcPt.y)
                val dest = matchBlockOne(data, src, destPt)
                if (dest != null) {
                    list.add(src)
                    list.add(dest)
                    return list
                }
            } else {
                break
            }
        }

        return null
    }
}