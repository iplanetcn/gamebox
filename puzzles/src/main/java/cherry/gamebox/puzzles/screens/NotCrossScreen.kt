package cherry.gamebox.puzzles.screens

import android.graphics.Point
import cherry.gamebox.core.Assets
import cherry.gamebox.puzzles.PuzzlesGame
import cherry.gamebox.puzzles.notcross.GameBoard
import cherry.gamebox.puzzles.notcross.LineSegment
import cherry.gamebox.puzzles.notcross.LineSegmentActor

/**
 * NotCrossScreen
 *
 * @author john
 * @since 2021-12-02
 */
class NotCrossScreen(game: PuzzlesGame) : BaseScreen(game) {

    init {
        val l1 = LineSegment(p = Point(-100,-100), q = Point(100,100))
        val l2 = LineSegment(p = Point(100,-100), q = Point(-100,100))
        val gameBoard = GameBoard()
        gameBoard.addActor(LineSegmentActor(l1))
        gameBoard.addActor(LineSegmentActor(l2))
        stage.addActor(gameBoard)
    }

    override fun draw(delta: Float) {
        batcher.begin()
        batcher.draw(Assets.backgrounds.background, 0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT)
        batcher.end()
    }

    override fun update(delta: Float) {

    }

}