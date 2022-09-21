package cherry.gamebox.tzfe.actor

import cherry.gamebox.tzfe.TzfeGame
import cherry.gamebox.tzfe.constant.Constant
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

/**
 * BottomGroup
 *
 * @author john
 * @since 2022-09-21
 */
class BottomGroup(game: TzfeGame) : BaseGroup(game) {
    /** 帮主按钮  */
    private var helpButton: Button

    /** 退出按钮  */
    private var exitButton: Button

    init {
        /*
		 * 帮助按钮
		 */
        val helpStyle = Button.ButtonStyle()
        helpStyle.up = TextureRegionDrawable(
            game.atlas.findRegion(Constant.AtlasNames.GAME_BTN_HELP, 1)
        )
        helpStyle.down = TextureRegionDrawable(
            game.atlas.findRegion(Constant.AtlasNames.GAME_BTN_HELP, 2)
        )
        helpButton = Button(helpStyle)
        helpButton.x = 15f
        // 设置按钮点击监听
        helpButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                // 显示帮助舞台
                game.gameScreen.setShowHelpStage(true)
            }
        })
        addActor(helpButton)

        // 设置组的尺寸（以世界的宽度, 按钮的高度 作为组的宽高）
        setSize(game.worldWidth, helpButton.height)

        /*
		 * 退出按钮
		 */
        val exitStyle = Button.ButtonStyle()
        exitStyle.up = TextureRegionDrawable(
            game.atlas.findRegion(Constant.AtlasNames.GAME_BTN_EXIT, 1)
        )
        exitStyle.down = TextureRegionDrawable(
            game.atlas.findRegion(Constant.AtlasNames.GAME_BTN_EXIT, 2)
        )
        exitButton = Button(exitStyle)
        exitButton.x = 240f
        // 设置按钮点击监听
        exitButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.gameScreen.setShowExitConfirmStage(true)
            }
        })
        addActor(exitButton)
    }
}