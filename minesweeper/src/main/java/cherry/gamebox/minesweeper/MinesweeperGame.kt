package cherry.gamebox.minesweeper

import cherry.gamebox.minesweeper.screen.MenuScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

/**
 * MinesweeperGame
 *
 * @author john
 * @since 2021-12-09
 */
class MinesweeperGame : Game() {
    val batch: SpriteBatch by lazy { SpriteBatch() }

    override fun create() {
        screen = MenuScreen(this)
    }

    override fun render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f)
        super.render()
    }

    override fun dispose() {
        super.dispose()
        batch.dispose()
    }
}