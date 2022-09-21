package cherry.gamebox.tzfe.stage

import cherry.gamebox.tzfe.TzfeGame
import cherry.gamebox.tzfe.constant.Constant
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.Viewport





/**
 * HelpStage
 *
 * @author john
 * @since 2022-09-21
 */
class HelpStage(mainGame: TzfeGame, viewport: Viewport) :
    BaseStage(mainGame, viewport) {
    /** 舞台背景颜色, 60% 黑色  */
    private val bgColor: Color = Color(0f, 0f, 0f, 0.6f)

    /** 背景  */
    private var bgImage: Image = Image(getMainGame().atlas.findRegion(Constant.AtlasNames.GAME_BLANK))

    /** 帮助内容图片  */
    private var helpContentImage: Image

    init {
        /*
         * 背景
         */
        // Constant.AtlasNames.GAME_BLANK 是一张纯白色的小图片
        bgImage.color = bgColor
        bgImage.setOrigin(0f, 0f)
        // 缩放到铺满整个舞台
        bgImage.setScale(width / bgImage.width, height / bgImage.height)
        addActor(bgImage)

        /*
         * 帮助内容的图片
         */helpContentImage = Image(getMainGame().atlas.findRegion(Constant.AtlasNames.GAME_HELP_BG))
        // 水平居中
        helpContentImage.x = width / 2 - helpContentImage.width / 2
        // 设置到舞台顶部
        helpContentImage.y = height - helpContentImage.height
        addActor(helpContentImage)

        /*
         * 添加舞台监听器（点击屏幕或按返回键返回主游戏舞台）
         */addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // 隐藏帮助舞台返回主游戏舞台）
                getMainGame().gameScreen.setShowHelpStage(false)
            }
        })
        addListener(object : InputListener() {
            override fun keyUp(event: InputEvent?, keycode: Int): Boolean {
                if (keycode == Input.Keys.BACK) {
                    // 按返回键, 隐藏帮助舞台（返回主游戏舞台）
                    getMainGame().gameScreen.setShowHelpStage(false)
                }
                return true
            }
        })
    }
}

