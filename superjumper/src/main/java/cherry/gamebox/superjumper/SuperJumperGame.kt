package cherry.gamebox.superjumper

import cherry.gamebox.core.CoreAssets
import cherry.gamebox.superjumper.config.VIEWPORT_HEIGHT
import cherry.gamebox.superjumper.config.VIEWPORT_WIDTH
import cherry.gamebox.superjumper.screen.AssetsScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.StretchViewport

/**
 * Solitaire
 *
 * @author john
 * @since 2021-12-09
 */
class SuperJumperGame : Game() {
    lateinit var batch: SpriteBatch
    lateinit var stage: Stage

    override fun create() {
        stage = Stage(StretchViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT))
        CoreAssets.load()
        Assets.load()
        batch = SpriteBatch()
        setScreen(AssetsScreen(this))
    }

    override fun dispose() {
        super.dispose()
        stage.dispose()
        CoreAssets.dispose()
        Assets.dispose()
        batch.dispose()
    }
}