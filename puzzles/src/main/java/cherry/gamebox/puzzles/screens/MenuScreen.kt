package cherry.gamebox.puzzles.screens

import cherry.gamebox.core.CoreAssets
import cherry.gamebox.puzzles.PuzzlesGame
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
class MenuScreen(game:  PuzzlesGame) : BaseScreen(game) {

    init {
        val buttonPlay = Label(
            "Play", Label.LabelStyle(
                CoreAssets.fonts.fontSmall, Color.ORANGE
            )
        )

        buttonPlay.setPosition(SCREEN_WIDTH / 2f - buttonPlay.width / 2f, SCREEN_HEIGHT / 2)
        addEffectToPress(buttonPlay)
        buttonPlay.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                changeScreenWithFadeOut(NotCrossScreen::class.java, game)
                CoreAssets.playSoundDrop()
            }
        })

        stage.addActor(buttonPlay)
    }

    override fun draw(delta: Float) {
        batcher.begin()
        batcher.draw(CoreAssets.backgrounds.background, 0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT)
        batcher.end()
    }

    override fun update(delta: Float) {

    }

}