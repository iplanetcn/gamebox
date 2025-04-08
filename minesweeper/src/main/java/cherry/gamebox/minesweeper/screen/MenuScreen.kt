package cherry.gamebox.minesweeper.screen

import cherry.gamebox.minesweeper.MinesweeperGame
import cherry.gamebox.minesweeper.SCREEN_HEIGHT
import cherry.gamebox.minesweeper.SCREEN_WIDTH
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.ScreenUtils

class MenuScreen(game: MinesweeperGame): BaseScreen(game) {

    override fun show() {
        super.show()
        game.stage
    }

    override fun render(delta: Float) {
        super.render(delta)
        ScreenUtils.clear(0.4f, 0.4f, 0.4f, 1f)
        game.renderer.projectionMatrix = game.camera.combined
        game.renderer.apply {
            begin(ShapeRenderer.ShapeType.Filled)
            color = Color.RED
            circle(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 100f, 64)
            end()
        }
    }
}