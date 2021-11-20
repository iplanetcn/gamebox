package cherry.gamebox.tetris.model

import cherry.gamebox.tetris.assets.Assets
import com.badlogic.gdx.graphics.g2d.TextureRegion
/**
 * BrickType
 *
 * @author john
 * @since 2021-11-19
 */
enum class BrickType {
    I, J, L, T, Z, S, O
}

enum class BrickColor {
    Blue, Orange, Purple, Red, Teal, Yellow;

    fun getTextureRegion(): TextureRegion {
        return when (this) {
            Blue -> Assets.blocks.blue
            Orange -> Assets.blocks.orange
            Purple -> Assets.blocks.purple
            Red -> Assets.blocks.red
            Teal -> Assets.blocks.teal
            Yellow -> Assets.blocks.yellow
        }
    }
}

data class Point(val x: Int, val y: Int) {
    companion object { val Zero = Point(0,0)}
}

class Brick(var type: BrickType, var color: BrickColor) {
    companion object {
        var nextBricks: MutableList<Brick> = mutableListOf()
        private const val nextBrickCount = 3

        private fun newBrick(): Brick {
            val brick = Brick(
                BrickType.values().random(),
                BrickColor.values().random()
            )

            brick.ty = -brick.vertical()
            return brick
        }

        fun generate(): Brick {
            while (nextBricks.size < nextBrickCount) {
                nextBricks.add(newBrick())
            }
            val brick = nextBricks.removeAt(0)
            nextBricks.add(newBrick())
            return brick
        }
    }

    var points = mutableListOf<Point>()
    var tx: Int = 4
    var ty: Int = 0

    init {
        when (type) {
            BrickType.I -> {
                points.add(Point(0, 0))
                points.add(Point(0, 1))
                points.add(Point(0, 2))
                points.add(Point(0, 3))
            }
            BrickType.J -> {
                points.add(Point(1, 0))
                points.add(Point(1, 1))
                points.add(Point(1, 2))
                points.add(Point(0, 2))
            }
            BrickType.L -> {
                points.add(Point(0, 0))
                points.add(Point(0, 1))
                points.add(Point(0, 2))
                points.add(Point(1, 2))
            }
            BrickType.T -> {
                points.add(Point(0, 0))
                points.add(Point(1, 0))
                points.add(Point(2, 0))
                points.add(Point(1, 1))
            }
            BrickType.Z -> {
                points.add(Point(1, 0))
                points.add(Point(0, 1))
                points.add(Point(1, 1))
                points.add(Point(0, 2))
            }
            BrickType.S -> {
                points.add(Point(0, 0))
                points.add(Point(0, 1))
                points.add(Point(1, 1))
                points.add(Point(1, 2))
            }
            BrickType.O -> {
                points.add(Point(0, 0))
                points.add(Point(0, 1))
                points.add(Point(1, 0))
                points.add(Point(1, 1))
            }
        }
    }

    fun moveDown() {
        ty += 1
    }

    fun moveLeft() {
        tx -= 1
    }

    fun moveRight() {
        tx += 1
    }


    fun rotatePoints(): MutableList<Point> {
        return when (type) {
            BrickType.O -> points
            else -> {
                var mx: Int = points.sumOf { it.x }
                var my: Int = points.sumOf { it.y }
                mx /= points.size
                my /= points.size

                val sinX = 1
                val cosX = 0

                val rotatedBrick = mutableListOf<Point>()

                points.forEach {
                    val r = it.y
                    val c = it.x
                    val x = (c - mx) * cosX - (r - my) * sinX
                    val y = (c - mx) * sinX + (r - my) * cosX
                    rotatedBrick.add(Point(x, y))
                }

                return rotatedBrick
            }
        }
    }

    fun left(): Point {
        var left = points[0]
        points.forEach {
            if (left.x > it.x) {
                left = it
            }
        }

        return left
    }

    fun right(): Point {
        var right = points[0]
        points.forEach {
            if (right.x < it.x) {
                right = it
            }
        }
        return right
    }

    fun bottom(): Point {
        var bottom = points[0]
        points.forEach {
            if (bottom.y < it.y) {
                bottom = it
            }
        }
        return bottom
    }

    fun top(): Point {
        var top = points[0]
        points.forEach {
            if (top.y > it.y) {
                top = it
            }
        }
        return top
    }

    // vertical length
    fun vertical(): Int {
        return bottom().y.toInt() + 1
    }
}
