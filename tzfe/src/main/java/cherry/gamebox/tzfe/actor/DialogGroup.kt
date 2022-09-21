package cherry.gamebox.tzfe.actor

import cherry.gamebox.tzfe.TzfeGame
import cherry.gamebox.tzfe.constant.Constant
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable


/**
 * DialogGroup
 *
 * @author john
 * @since 2022-09-21
 */
class DialogGroup(game: TzfeGame, message: String) : BaseGroup(game) {
    /** 背景颜色  */
    private val bgColor: Color = Color(-0x13131400 or (255 * 0.95f).toInt())

    /** 文本标签的字体颜色  */
    private val msgTextColor: Color = Color(0x777777FF)

    /** 按钮透明度  */
    private val btnAlpha = 0.95f

    /** 背景图片  */
    private var bgImage: Image

    /** 对话框文本提示标签  */
    private var msgLabel: Label

    /** 确认按钮  */
    private var okButton: Button

    /** 取消按钮  */
    private var cancelButton: Button

    init {
        width = Constant.DIALOG_WIDTH
        /*
         * 背景
         */
        // Constant.AtlasNames.GAME_BLANK 是一张纯白色的小图片
        bgImage = Image(game.atlas.findRegion(Constant.AtlasNames.GAME_BLANK))
        bgImage.color = bgColor
        bgImage.setOrigin(0f, 0f)
        // 水平方向先缩放到铺满对话框宽度
        bgImage.scaleX = width / bgImage.width
        addActor(bgImage)

        /*
         * 确定按钮
         */
        val okBtnStyle: Button.ButtonStyle = ImageTextButtonStyle()
        okBtnStyle.up = TextureRegionDrawable(
            game.atlas.findRegion(Constant.AtlasNames.DIALOG_BTN_OK, 1)
        )
        okBtnStyle.down = TextureRegionDrawable(
            game.atlas.findRegion(Constant.AtlasNames.DIALOG_BTN_OK, 2)
        )
        okButton = Button(okBtnStyle)
        okButton.setPosition(width - okButton.width - 10, 10f)
        okButton.color.a = btnAlpha
        addActor(okButton)

        /*
         * 取消按钮
         */
        val cancelBtnStyle: Button.ButtonStyle = ImageTextButtonStyle()
        cancelBtnStyle.up = TextureRegionDrawable(
            game.atlas.findRegion(Constant.AtlasNames.DIALOG_BTN_cancel, 1)
        )
        cancelBtnStyle.down = TextureRegionDrawable(
            game.atlas.findRegion(Constant.AtlasNames.DIALOG_BTN_cancel, 2)
        )
        cancelButton = Button(cancelBtnStyle)
        cancelButton.setPosition(10f, 10f)
        cancelButton.color.a = btnAlpha
        addActor(cancelButton)

        /*
         * 对话框文本提示标签
         */
        val labelStyle = Label.LabelStyle()
        labelStyle.font = game.bitmapFont
        labelStyle.fontColor = msgTextColor
        msgLabel = Label(message, labelStyle)
        // 设置字体大小
        msgLabel.setFontScale(0.5f)
        // 标签包裹字体
        msgLabel.setSize(msgLabel.prefWidth, msgLabel.prefHeight)
        msgLabel.x = width / 2 - msgLabel.width / 2
        msgLabel.y = okButton.y + okButton.height + 50
        addActor(msgLabel)

        /*
         * 根据对话框中的控件计算对话框高度
         */
        height = msgLabel.y + msgLabel.height + 50

        // 已知对话框高度后, 将背景竖直方向缩放到铺满对话框高度
        bgImage.scaleY = height / bgImage.height
    }

    fun getOkButton(): Button {
        return okButton
    }

    fun getCancelButton(): Button {
        return cancelButton
    }

}