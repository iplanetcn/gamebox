package cherry.gamebox.tzfe.actor

import cherry.gamebox.tzfe.TzfeGame
import cherry.gamebox.tzfe.constant.Constant
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label

/**
 * CardGroup
 *
 * @author john
 * @since 2022-09-21
 */
class CardGroup(game: TzfeGame) : BaseGroup(game) {
    /** 卡片背景 */
    private var bgImage: Image

    /** 卡片显示的数字标签 */
    private var numLabel: Label

    /** 卡片当前显示的数字 */
    private var num = 0

    init {
        // 卡片背景
        bgImage = Image(game.atlas.findRegion(Constant.AtlasNames.GAME_ROUND_RECT))
        addActor(bgImage)
        // 设置组的宽高（以卡片背景的宽高作为组的宽高）
        setSize(bgImage.width, bgImage.height)
        // 创建标签样式
        val style = Label.LabelStyle()
        style.font = game.bitmapFont
        style.fontColor = Color.BLACK
        // 创建文本标签
        numLabel = Label("0", style)

        // 设置字体缩放
        numLabel.setFontScale(0.48f)

        // 设置标签的宽高（把标签的宽高设置为文本字体的宽高, 即标签包裹文本）
        numLabel.setSize(numLabel.prefWidth, numLabel.prefHeight)

        // 设置文本标签在组中居中显示
        numLabel.x = width / 2 - numLabel.width / 2
        numLabel.y = height / 2 - numLabel.height / 2
        addActor(numLabel)

        // 初始化显示
        setNum(num)
    }

    fun setNum(num: Int) {
        this.num = num
        if (this.num == 0) {
            // 如果是 0, 不显示文本
            numLabel.setText("")
        } else {
            numLabel.setText("" + this.num)
        }

        // 重新设置文本后, 文本的宽度可能被改变, 需要重新设置标签的宽度, 并重新水平居中
        numLabel.width = numLabel.prefWidth
        numLabel.x = width / 2 - numLabel.width / 2
        when (this.num) {
            2 -> {
                bgImage.color = Constant.CardColors.RGBA_2
            }
            4 -> {
                bgImage.color = (Constant.CardColors.RGBA_4)
            }
            8 -> {
                bgImage.color = (Constant.CardColors.RGBA_8)
            }
            16 -> {
                bgImage.color = (Constant.CardColors.RGBA_16)
            }
            32 -> {
                bgImage.color = (Constant.CardColors.RGBA_32)
            }
            64 -> {
                bgImage.color = (Constant.CardColors.RGBA_64)
            }
            128 -> {
                bgImage.color = (Constant.CardColors.RGBA_128)
            }
            256 -> {
                bgImage.color = (Constant.CardColors.RGBA_256)
            }
            512 -> {
                bgImage.color = (Constant.CardColors.RGBA_512)
            }
            1024 -> {
                bgImage.color = (Constant.CardColors.RGBA_1024)
            }
            2048 -> {
                bgImage.color = (Constant.CardColors.RGBA_2048)
            }
            else -> {
                bgImage.color = (Constant.CardColors.RGBA_0)
            }
        }
    }
}