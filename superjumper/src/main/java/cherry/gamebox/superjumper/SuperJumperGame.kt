package cherry.gamebox.superjumper

import cherry.gamebox.core.CoreAssets
import cherry.gamebox.superjumper.controller.GameControllerManager
import cherry.gamebox.superjumper.screen.AssetsScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
/**
 * Solitaire
 *
 * @author john
 * @since 2021-12-09
 */
class SuperJumperGame : Game() {
    lateinit var batch: SpriteBatch
    lateinit var controllerManager: GameControllerManager

    override fun create() {
        CoreAssets.load()
        Assets.load()
        batch = SpriteBatch()
        controllerManager = GameControllerManager()
        setScreen(AssetsScreen(this))
    }

    override fun render() {
        super.render()
        controllerManager.update(Gdx.graphics.deltaTime)
        controllerManager.render(batch)
    }

    override fun dispose() {
        super.dispose()
        CoreAssets.dispose()
        Assets.dispose()
        controllerManager.dispose()
        batch.dispose()
    }
}