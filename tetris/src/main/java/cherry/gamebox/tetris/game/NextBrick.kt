package cherry.gamebox.tetris.game

import cherry.gamebox.tetris.Assets
import cherry.gamebox.tetris.model.Brick
import cherry.gamebox.tetris.model.Point
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image


/**
 * NextBrick
 *
 * @author john
 * @since 2021-11-19
 */
class NextBrick : Group() {
    private val offsetY = 5
    private val offsetX = 0

    init {
        if (Brick.nextBricks.isEmpty()) {
            Brick.generate()
        }
        updateBrick()
    }

    private fun addBackground() {
        val background = Image(Assets.backgrounds.gameBoard)
        background.setSize(
            (5 * Assets.blockWidth).toFloat(),
            (16 * Assets.blockHeight).toFloat()
        )
        background.setPosition(-Assets.blockWidth.toFloat(), - Assets.blockWidth.toFloat())
        addActor(background)
    }

    private fun addBrickBlocks() {
        if (Brick.nextBricks.isEmpty()) {
            return
        }

        var start = Point.Zero
        Brick.nextBricks.forEach { brick ->
            brick.points.forEach { pos ->
                val block = Image(brick.color.getTextureRegion())
                val bx = (start.x + pos.x) * block.width
                val by = (start.y + pos.y) * block.height
                block.setPosition(bx, by)
                addActor(block)
            }
            start = Point(start.x + offsetX, start.y + offsetY)
        }
    }

    fun updateBrick() {
        clear()
        addBackground()
        addBrickBlocks()
    }


}