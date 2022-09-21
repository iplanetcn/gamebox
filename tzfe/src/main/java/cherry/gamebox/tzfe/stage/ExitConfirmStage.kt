package cherry.gamebox.tzfe.stage

import cherry.gamebox.tzfe.TzfeGame
import cherry.gamebox.tzfe.actor.DialogGroup
import cherry.gamebox.tzfe.constant.Constant
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.Viewport


/**
 * ExitConfirmStage
 *
 * @author john
 * @since 2022-09-21
 */
class ExitConfirmStage(mainGame: TzfeGame, viewport: Viewport) :
    BaseStage(mainGame, viewport) {
    /** 舞台背景颜色, 60% 黑色  */
    private val bgColor: Color = Color(0f, 0f, 0f, 0.6f)

    /** 背景  */
    private var bgImage: Image = Image(getMainGame().atlas.findRegion(Constant.AtlasNames.GAME_BLANK))

    /** 确认对话框  */
    private var dialogGroup: DialogGroup

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
         * 创建对话框
         */dialogGroup = DialogGroup(getMainGame(), "确定退出游戏 ?")
        // 将对话框居中到舞台
        dialogGroup.setPosition(
            width / 2 - dialogGroup.width / 2,
            height / 2 - dialogGroup.height / 2
        )

        // 给对话框的确定按钮添加监听器
        dialogGroup.getOkButton().addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                // 点击确定按钮, 退出应用
                Gdx.app.exit()
            }
        })

        // 给对话框的确定按钮添加监听器
        dialogGroup.getCancelButton().addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                // 点击取消按钮, 隐藏对话框（隐藏退出确认舞台, 返回主游戏舞台）
                getMainGame().gameScreen.setShowExitConfirmStage(false)
            }
        })

        // 添加对话框到舞台
        addActor(dialogGroup)

        /*
    	 * 添加舞台输入监听器
    	 */addListener(InputListenerImpl())
    }

    /**
     * 输入事件监听器
     */
    private inner class InputListenerImpl : InputListener() {
        override fun keyUp(event: InputEvent?, keycode: Int): Boolean {
            if (keycode == Input.Keys.BACK) {
                // 按返回键, 隐藏退出确认舞台（返回主游戏舞台）
                getMainGame().gameScreen.setShowExitConfirmStage(false)
                return true
            }
            return super.keyUp(event, keycode)
        }
    }
}