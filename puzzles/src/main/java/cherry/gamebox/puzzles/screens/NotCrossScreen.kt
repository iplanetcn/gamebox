package cherry.gamebox.puzzles.screens

import cherry.gamebox.core.Assets
import cherry.gamebox.core.GameLogger
import cherry.gamebox.puzzles.PuzzlesGame
import cherry.gamebox.puzzles.notcross.GameBoard
import cherry.gamebox.puzzles.notcross.LineSegment
import cherry.gamebox.puzzles.notcross.LineSegmentActor
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

/**
 * NotCrossScreen
 *
 * @author john
 * @since 2021-12-02
 */

const val OFFSET_X = SCREEN_WIDTH / 2
const val OFFSET_Y = SCREEN_HEIGHT / 2

class NotCrossScreen(game: PuzzlesGame) : BaseScreen(game) {
    val l1 = LineSegment(
        p = Vector2(OFFSET_X + 0f, OFFSET_Y + 0f),
        q = Vector2(OFFSET_X + 100f, OFFSET_Y + 100f)
    )
    val l2 = LineSegment(
        p = Vector2(OFFSET_X + 200f, OFFSET_Y + 0f),
        q = Vector2(OFFSET_X + 0f, OFFSET_Y + 300f)
    )

    init {
        val gameBoard = GameBoard()
        gameBoard.addActor(LineSegmentActor(l1))
        gameBoard.addActor(LineSegmentActor(l2))
        stage.addActor(gameBoard)
    }

    override fun draw(delta: Float) {
        batcher.begin()
        batcher.draw(Assets.backgrounds.background, 0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT)
        batcher.end()

        Gdx.gl20.glLineWidth(10f)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.setColor(1f,0f,0f,1f)
        shapeRenderer.line(l1.p, l1.q)
        shapeRenderer.line(l2.p, l2.q)
        shapeRenderer.end()

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.setColor(1f,0f,0f,1f)
        shapeRenderer.circle(l1.p.x, l1.p.y, 20f)
        shapeRenderer.circle(l1.q.x, l1.q.y, 20f)
        shapeRenderer.circle(l2.p.x, l2.p.y, 20f)
        shapeRenderer.circle(l2.q.x, l2.q.y, 20f)
        shapeRenderer.end()
    }

    override fun update(delta: Float) {

    }

}