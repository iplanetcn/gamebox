package cherry.gamebox.puzzles.notcross

import cherry.gamebox.core.Assets
import cherry.gamebox.puzzles.screens.SCREEN_HEIGHT
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image

/**
 * LineSegmentActor
 *
 * @author john
 * @since 2021-12-02
 */
class LineSegmentActor(var lineSegment: LineSegment) : Group() {
    init {
        val pImage = Image(Assets.blocks.blue)
        pImage.setPosition(lineSegment.p.x, lineSegment.p.y)
        val qImage = Image(Assets.blocks.blue)
        qImage.setPosition(lineSegment.q.x, lineSegment.q.y)
        addActor(pImage)
        addActor(qImage)
    }
}