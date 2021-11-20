package cherry.gamebox.tetris.game

import cherry.gamebox.tetris.assets.Assets
import cherry.gamebox.tetris.model.BrickColor
import cherry.gamebox.tetris.model.Point
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

/**
 * SmartBlock
 *
 * @author john
 * @since 2021-11-20
 */
class SmartBlock(var point: Point = Point.Zero, var color: BrickColor = BrickColor.Blue) : Image() {

    fun update(target: BrickColor) {
        drawable = TextureRegionDrawable(TextureRegion(color.getTextureRegion()))
        color = target
    }

    fun moveTo(target: Point) {
        addAction(
            Actions.moveTo(
                (target.x - point.x) * Assets.blockWidth.toFloat(),
                (target.y - point.y) * Assets.blockHeight.toFloat(),
                0.3f
            )
        )

        point = target
    }

}