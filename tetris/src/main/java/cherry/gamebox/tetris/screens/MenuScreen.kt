package cherry.gamebox.tetris.screens

import cherry.gamebox.tetris.TetrisGame
import cherry.gamebox.tetris.assets.Assets
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

/**
 * MenuScreen
 *
 * @author john
 * @since 2021-11-17
 */
class MenuScreen(game: TetrisGame) : BaseScreen(game) {

    init {
        val buttonPlay = Label(
            "Play", Label.LabelStyle(
                Assets.fonts.fontSmall, Color.ORANGE
            )
        )

        buttonPlay.setPosition(SCREEN_WIDTH / 2f - buttonPlay.width / 2f, SCREEN_HEIGHT / 2)
        addEffectToPress(buttonPlay)
        buttonPlay.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                changeScreenWithFadeOut(GameScreen::class.java, game)
                Assets.playSoundDrop()
            }
        })

        stage.addActor(buttonPlay)
    }

    override fun draw(delta: Float) {
        batcher.begin()
        batcher.draw(Assets.backgrounds.background, 0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT)
        batcher.end()
    }

    override fun update(delta: Float) {

    }

}