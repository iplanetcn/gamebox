package cherry.gamebox.fruit

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class FruitGame : Game() {
    lateinit var batch: SpriteBatch
    lateinit var font: BitmapFont
    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont(
            Gdx.files.internal("font/bmf1.fnt"),
            Gdx.files.internal("font/bmf1.png"),
            false
        )
        font.data.setScale(1f)
        setScreen(MainMenuScreen(this))
    }

    override fun render() {
        super.render() // important!
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
    }
}