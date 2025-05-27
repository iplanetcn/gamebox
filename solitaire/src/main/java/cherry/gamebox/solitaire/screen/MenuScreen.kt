package cherry.gamebox.solitaire.screen

import cherry.gamebox.core.Assets
import cherry.gamebox.solitaire.SolitaireGame
import cherry.gamebox.solitaire.config.SCREEN_HEIGHT
import cherry.gamebox.solitaire.config.SCREEN_WIDTH
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

/**
 * MenuScreen
 *
 * @author john
 * @since 2021-12-09
 */
class MenuScreen(
    game: SolitaireGame
): BaseScreen(game) {
    init {
        val buttonPlay = Label("Play", Label.LabelStyle(Assets.fonts.fontNormal, Color.WHITE))
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

    }

    override fun update(delta: Float) {}

}