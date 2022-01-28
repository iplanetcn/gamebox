package cherry.gamebox.snake.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import kotlin.math.min


/**
 * ChessBoard
 *
 * @author john
 * @since 2021-07-13
 */
class GameBoard(
    private var rows: Int,
    private var cols: Int,
    private var screenWidth: Int,
    private var screenHeight: Int
) {
    private var gridSize: Float
    private var boardRect: Rectangle
    private val gridBoxList: MutableList<GridBox>
    private val gridBoxMap: MutableMap<String, GridBox>
    private val offset: Float = 16f

    init {
        gridSize = calculateGridSize()
        boardRect = calculateBoardRect()
        gridBoxList = mutableListOf()
        gridBoxMap = mutableMapOf()
        calculateGridBox()
    }

    /**
     * calculate grid size
     */
    private fun calculateGridSize(): Float {
        val maxBoardWidth = screenWidth - 2 * offset
        val maxBoardHeight = screenHeight - 2 * offset

        val w = maxBoardWidth / cols
        val h = maxBoardHeight / rows

        return min(w, h)

    }

    // 计算外框
    private fun calculateBoardRect(): Rectangle {
        // calculate left right top bottom
        val left = (screenWidth - gridSize * cols) / 2
        val right = screenWidth - left
        val bottom = (screenHeight - gridSize * rows) / 2
        val top = bottom + gridSize * rows
        return Rectangle(left, bottom, right - left, top - bottom)
    }

    // 计算网格
    private fun calculateGridBox() {
        if (gridBoxList.isNotEmpty()) {
            gridBoxList.clear()
        }

        if (gridBoxMap.isNotEmpty()) {
            gridBoxMap.clear()
        }

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                val boxCenterX = boardRect.x + j * gridSize + gridSize / 2
                val boxCenterY = boardRect.y + i * gridSize + gridSize / 2
                val boxCenterPosition = Vector2(boxCenterX, boxCenterY)
                val gridBox = GridBox(Index(i, j), boxCenterPosition, gridSize)
                gridBoxList.add(gridBox)
                gridBoxMap[generateKeyByIndex(Index(i, j))] = gridBox
            }
        }
    }

    /**
     * render chess board
     */
    fun renderBoard(shapeRenderer: ShapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.WHITE
        // draw horizontal line
        for (i in 0..rows) {
            shapeRenderer.rectLine(
                Vector2(boardRect.x, gridSize * i + boardRect.y),
                Vector2(boardRect.x + boardRect.width, gridSize * i + boardRect.y),
                4f
            )
        }
        // draw vertical line
        for (i in 0..cols) {
            shapeRenderer.rectLine(
                Vector2(gridSize * i + boardRect.x, boardRect.y),
                Vector2(gridSize * i + boardRect.x, boardRect.y + boardRect.height),
                4f
            )
        }
        shapeRenderer.end()

        renderInfo(shapeRenderer)
    }

    private fun renderInfo(shapeRenderer: ShapeRenderer) {
        // draw each grid box center point
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.GREEN
        gridBoxList.forEach {
            shapeRenderer.circle(
                it.pos.x,
                it.pos.y,
                5f
            )
        }
        shapeRenderer.end()

        // draw each grid box
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.GREEN
        gridBoxList.forEach {
            shapeRenderer.rect(
                it.rect.x,
                it.rect.y,
                it.rect.width,
                it.rect.height
            )
        }
        shapeRenderer.end()

        // draw board outline rectangle
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.RED
        shapeRenderer.rect(
            boardRect.x,
            boardRect.y,
            boardRect.width,
            boardRect.height
        )
        shapeRenderer.end()
    }

    /**
     * check the point is inside board
     */
    fun isInsideBoard(pos: Vector2): Boolean {
        return boardRect.contains(pos.x, pos.y)
    }

    /**
     * get GridBox Center position
     */
    fun getGridBoxCenterPosition(pos: Vector2): GridBox? {
        gridBoxList.forEach {
            if (it.rect.contains(pos.x, pos.y)) {
                return it
            }
        }

        return null
    }

    /**
     * generate grid box key
     */
    private fun generateKeyByIndex(index: Index): String {
        return "${index.row}${index.col}"
    }
}