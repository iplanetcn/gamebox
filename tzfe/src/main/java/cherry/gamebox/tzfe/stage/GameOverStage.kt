package cherry.gamebox.tzfe.stage

import cherry.gamebox.tzfe.TzfeGame
import cherry.gamebox.tzfe.constant.Constant
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.Viewport



/**
 * GameOverStage
 *
 * @author john
 * @since 2022-09-21
 */
class GameOverStage(mainGame: TzfeGame, viewport: Viewport) :
    BaseStage(mainGame, viewport) {
    /** 舞台背景颜色, 60% 黑色  */
    private val bgColor: Color = Color(0f, 0f, 0f, 0.6f)

    /** 提示文本的颜色  */
    private val msgColor: Color = Color(-0x1)

    /** 背景  */
    private var bgImage: Image = Image(getMainGame().atlas.findRegion(Constant.AtlasNames.GAME_BLANK))

    /** 游戏结束文本提示标签  */
    private var msgLabel: Label

    /** 返回按钮  */
    private var backButton: Button

    /** 再来一局按钮  */
    private var againButton: Button

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
         * 游戏结束文本提示标签
         */
        val msgStyle = Label.LabelStyle()
        msgStyle.font = getMainGame().bitmapFont
        msgStyle.fontColor = msgColor
        msgLabel = Label("", msgStyle)
        msgLabel.setFontScale(0.7f)
        addActor(msgLabel)

        /*
         * 返回按钮
         */
        val backStyle = Button.ButtonStyle()
        backStyle.up =
            TextureRegionDrawable(getMainGame().atlas.findRegion(Constant.AtlasNames.BTN_BACK, 1))
        backStyle.down =
            TextureRegionDrawable(getMainGame().atlas.findRegion(Constant.AtlasNames.BTN_BACK, 2))
        backButton = Button(backStyle)
        backButton.x = 40f
        backButton.y = 140f
        backButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                // 点击返回按钮, 隐藏结束舞台（返回主游戏舞台）
                getMainGame().gameScreen.hideGameOverStage()
            }
        })
        addActor(backButton)

        /*
         * 再来一局按钮
         */
        val againStyle = Button.ButtonStyle()
        againStyle.up =
            TextureRegionDrawable(getMainGame().atlas.findRegion(Constant.AtlasNames.BTN_AGAIN, 1))
        againStyle.down =
            TextureRegionDrawable(getMainGame().atlas.findRegion(Constant.AtlasNames.BTN_AGAIN, 2))
        againButton = Button(againStyle)
        againButton.x = width - againButton.width - 40
        againButton.y = 140f
        againButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                // 隐藏结束舞台（返回主游戏舞台）
                getMainGame().gameScreen.hideGameOverStage()
                // 重新初始化游戏
                getMainGame().gameScreen.restartGame()
            }
        })
        addActor(againButton)

        /*
    	 * 添加舞台输入监听器
    	 */addListener(InputListenerImpl())
    }

    fun setGameOverState(isWin: Boolean, score: Int) {
        if (isWin) {
            msgLabel.setText("恭喜您 , 游戏过关 !\n分数: $score")
        } else {
            msgLabel.setText("游戏结束 !\n分数: $score")
        }

        /*
    	 * 设置了文本后重新设置标签的宽高以及位置
    	 */
        // 标签包裹字体
        msgLabel.setSize(msgLabel.prefWidth, msgLabel.prefHeight)
        msgLabel.x = 40f
        msgLabel.y = height - msgLabel.height - 100
    }

    /**
     * 输入事件监听器
     */
    private inner class InputListenerImpl : InputListener() {
        override fun keyUp(event: InputEvent?, keycode: Int): Boolean {
            if (keycode == Input.Keys.BACK) {
                // 按返回键, 隐藏游戏结束舞台（返回主游戏舞台）
                getMainGame().gameScreen.hideGameOverStage()
                return true
            }
            return super.keyUp(event, keycode)
        }
    }
}