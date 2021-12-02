package cherry.gamebox.puzzles.notcross

import cherry.gamebox.core.Assets
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image

/**
 * LineSegmentActor
 *
 * @author john
 * @since 2021-12-02
 */
class LineSegmentActor(lineSegment: LineSegment) : Group() {
    init {
        val pImage = Image(Assets.blocks.blue)
        pImage.setPosition(lineSegment.p.x.toFloat(), lineSegment.p.y.toFloat())
        val qImage = Image(Assets.blocks.blue)
        qImage.setPosition(lineSegment.q.x.toFloat(), lineSegment.q.y.toFloat())
        addActor(pImage)
        addActor(qImage)
    }
}