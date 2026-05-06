package cherry.gamebox.minesweeper.screen

import cherry.gamebox.minesweeper.MinesweeperGame
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport


class MenuScreen(game: MinesweeperGame): BaseScreen(game) {
    private val uiStage: Stage
    private val skin: Skin
    private val playButton: TextButton
    private val exitButton: TextButton

    init {
        uiStage = Stage(ScreenViewport(), game.batch)
        Gdx.input.inputProcessor = uiStage

        skin = Skin(Gdx.files.internal("images/uiskin.json"))

        // Create buttons
        playButton = TextButton("Play Game", skin)
        exitButton = TextButton("Exit", skin)


        // Set positions
        playButton.setPosition(100f, 200f)
        exitButton.setPosition(100f, 120f)
        playButton.setSize(200f, 50f)
        exitButton.setSize(200f, 50f)


        // Add listeners
        playButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                (Gdx.app.applicationListener as Game).setScreen(GameScreen(game))
            }
        })

        exitButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                Gdx.app.exit()
            }
        })

        uiStage.addActor(playButton)
        uiStage.addActor(exitButton)
    }

    override fun show() {

    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.6f, 0.6f, 0.4f, 1f)
        uiStage.act(delta);
        uiStage.draw();
    }

    override fun resize(width: Int, height: Int) {
        uiStage.viewport.update(width, height, true)

    }
}