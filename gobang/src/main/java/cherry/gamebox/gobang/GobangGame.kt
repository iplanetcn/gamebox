package cherry.gamebox.gobang

import cherry.gamebox.gobang.utils.RenderHelper
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import java.util.*

/**
 * GobangGame
 *
 * @author john
 * @since 2021-07-12
 */
const val BOARD_LENGTH = 9

class GobangGame : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var camera: OrthographicCamera
    private lateinit var pos: Vector2
    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var chessBoard: ChessBoard
    private lateinit var assets: Assets
    private var screenWidth: Float = 0f
    private var screenHeight: Float = 0f
    private val cols = BOARD_LENGTH
    private val rows = BOARD_LENGTH
    private var steps = Stack<Step>()
    private val bgColor = Color(139 / 255f, 178 / 255f, 255 / 255f, 1.000f)

    //region Game Lifecycle
    override fun create() {
        assets = Assets()
        assets.load()
        batch = SpriteBatch()
        screenWidth = Gdx.graphics.width.toFloat()
        screenHeight = Gdx.graphics.height.toFloat()
        camera = OrthographicCamera()
        camera.setToOrtho(false, screenWidth, screenHeight)
        pos = Vector2.Zero
        pos.x = screenWidth / 2
        pos.y = screenHeight / 2
        shapeRenderer = ShapeRenderer()
        chessBoard = ChessBoard(rows, cols, screenWidth, screenHeight)
    }

    override fun resize(width: Int, height: Int) {}

    override fun render() {
        // touch event
        if (Gdx.input.isTouched) {
            pos = getInputPosition()
        }

        if (Gdx.input.justTouched()) {
            // 点击位置
            val touchPos = getInputPosition()

            // 对齐grid位置
            if (chessBoard.isInsideBoard(touchPos)) {
                val touchGridBox = chessBoard.getGridBoxCenterPosition(touchPos)
                if (touchGridBox != null) {
                    // 判断steps中是否已经该位置是否已经存在
                    var existInStep = false

                    run loop@{
                        steps.forEach {
                            if (it.index == touchGridBox.index) {
                                existInStep = true
                                return@loop
                            }
                        }
                    }

                    // 添加步骤
                    if (!existInStep) {
                        val step =
                            if (steps.isNotEmpty() && steps.peek().chessType == ChessType.Black) {
                                Step(touchGridBox.index, ChessType.White)
                            } else {
                                Step(touchGridBox.index, ChessType.Black)
                            }

                        steps.push(step)
                        chessBoard.addStepToChessMap(step)

                        judgeWinner()
                    }
                }
            }
        }

        // logic
        camera.update()
        // 设置背景色
        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.projectionMatrix = camera.combined
        batch.begin()
        chessBoard.renderBoard(shapeRenderer)
        RenderHelper.renderTouchCoordinate(shapeRenderer, pos)
        batch.end()
    }

    override fun pause() {}

    override fun resume() {}

    override fun dispose() {
        batch.dispose()
        shapeRenderer.dispose()
        assets.dispose()
    }
    //endregion


    /**
     * 获取输入位置
     */
    private fun getInputPosition(): Vector2 {
        val inputPos = Vector3()
        inputPos.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0F)
        camera.unproject(inputPos)
        return Vector2(inputPos.x, inputPos.y)
    }


    private fun judgeWinner() {
        // TODO implementation
    }



}