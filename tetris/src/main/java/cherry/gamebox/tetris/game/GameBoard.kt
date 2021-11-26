package cherry.gamebox.tetris.game

import cherry.gamebox.tetris.Assets
import cherry.gamebox.tetris.model.Brick
import cherry.gamebox.tetris.model.BrickColor
import cherry.gamebox.tetris.model.Point
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image

/**
 * GameBoard
 *
 * @author john
 * @since 2021-11-19
 */
class GameBoard : Group() {
    private lateinit var board: MutableList<MutableList<BrickColor?>>
    var currentBrick: Brick? = null

    var boardBlocks: MutableList<SmartBlock> = mutableListOf()
    var currentBrickBlocks: MutableList<SmartBlock> = mutableListOf()

    init {
        clearBoard()
        addBackground()
    }

    fun generateRow(): MutableList<BrickColor?> {
        val row = mutableListOf<BrickColor?>()
        for (i in 0 until Config.Cols) {
            row.add(null)
        }

        return row
    }

    fun dropBrick() {
        currentBrick?.apply {
            while (canMoveDown(this)) {
                moveDown()
            }
            Assets.playSoundDrop()
        }
    }

    fun rotateBrick() {
        currentBrick?.apply {
            if (canRotate(this, rotatePoints())) {
                points = rotatePoints()
                Assets.playSoundDrop()
            }
        }
    }

    private fun canRotate(brick: Brick, rotatedPoints: MutableList<Point>): Boolean {
        rotatedPoints.forEach { p ->
            val r = p.y + brick.ty
            val c = p.x + brick.tx
            if (r < 0 || r >= Config.Rows) {
                return false
            }
            if (c < 0 || c >= Config.Cols) {
                return false
            }
            if (board[r][c] != null) {
                return false
            }
        }
        return true
    }

    private fun canMoveDown(brick: Brick): Boolean {
        for (p in brick.points) {
            val r = p.y + brick.ty + 1

            // not visible brick points
            if (r < 0) {
                continue
            }
            // reach to bottom
            if (r >= Config.Rows) {
                return false
            }
            val c = p.x + brick.tx
            if (board[r][c] != null) {
                return false
            }
        }
        return true
    }

    fun update(): Pair<Boolean, Boolean> {
        currentBrick?.apply {
            var droppedBrick = false
            if (canMoveDown(this)) {
                moveDown()
            } else {
                droppedBrick = true
                points.forEach { p ->
                    val r = p.y + ty
                    val c = p.x + tx

                    // check game over
                    // can't move down and brick is out of top bound.
                    if (r < 0) {
                        return Pair(first = true, second = false)
                    }
                    board[r][c] = color
                }
                lineClear()
                generateBrick()
            }

            return Pair(first = true, second = droppedBrick)
        }

        return Pair(first = false, second = false)
    }

    private fun lineClear() {
        var lineCount = 0
        val linesToRemove = mutableListOf<Int>()

        for (i in 0 until board.size) {
            val row = board[i]
            val rows = row.filterNotNull()
            if (rows.size == Config.Cols) {
                linesToRemove.add(i)
                lineCount += 1
            }
        }

        linesToRemove.forEach { line ->
            board.removeAt(line)
            board.add(0, generateRow())
        }

        // 通知已消除行
    }

    fun updateX(x: Int) {
        currentBrick?.apply {
            if (x > 0) {
                var canMoveRight = right().x + tx + 1 <= Config.Cols - 1
                if (canMoveRight) {
                    for (p in points) {
                        val r = p.y + ty
                        val c = p.x + tx + 1
                        if (r < 0) {
                            continue
                        }

                        if (board[r][c] != null) {
                            canMoveRight = false
                            break
                        }
                    }

                    if (canMoveRight) {
                        moveRight()
                    }
                }
            } else if (x < 0) {
                var canMoveLeft = left().x + tx - 1 >= 0
                if (canMoveLeft) {
                    for (p in points) {
                        val r = p.y + ty
                        val c = p.x + tx - 1

                        if (r < 0) {
                            continue
                        }

                        if (board[r][c] != null) {
                            canMoveLeft = false
                            break
                        }
                    }
                }

                if (canMoveLeft) {
                    moveLeft()
                }
            }
        }
    }

    private fun clearBoard() {
        currentBrick = null
        board = mutableListOf()
        for (i in 0 until Config.Rows) {
            board.add(generateRow())
        }
    }


    val topY by lazy {
        3 * Assets.blockHeight
    }

    val bottomY by lazy {
        (Config.Rows - 1) * Assets.blockHeight
    }

    val centerX by lazy {
        currentBrick!!.tx * Assets.blockWidth
    }

    fun generateBrick() {
        currentBrick = Brick.generate()
    }


    private fun addBackground() {
        val background = Image(Assets.backgrounds.gameBoard)
        background.setSize(
            (Config.Cols * Assets.blockWidth).toFloat(),
            (Config.Rows * Assets.blockHeight).toFloat()
        )
        background.setPosition(0f, 0f)
        addActor(background)
    }

    private fun addBrickBlocks() {
        // draw game board
        for (r in 0 until Config.Rows) {
            for (c in 0 until Config.Cols) {
                val color = board[r][c]
                color?.let {
                    val block = Image(color.getTextureRegion())
                    block.setPosition(c * block.width, (Config.Rows - r - 1) * block.height)
                    addActor(block)
                    boardBlocks.add(SmartBlock( Point(c, r),it))
                }
            }
        }

        // draw current brick
        currentBrick?.apply {
            points.forEach { p ->
                val r = p.y + ty
                val c = p.x + tx
                if (r > 0) {
                    val block = Image(color.getTextureRegion())
                    val bx = c * block.width
                    val by = (Config.Rows - r) * block.height
                    block.setPosition(bx, by)
                    addActor(block)
                    boardBlocks.add(SmartBlock(Point(c, r), color))
                }
            }
        }

    }

    fun updateBrick() {
        clear()
        addBackground()
        addBrickBlocks()
    }


}