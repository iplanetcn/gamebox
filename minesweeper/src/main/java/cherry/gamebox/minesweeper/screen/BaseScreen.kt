package cherry.gamebox.minesweeper.screen

import cherry.gamebox.minesweeper.MinesweeperGame
import com.badlogic.gdx.ScreenAdapter

open class BaseScreen(val game: MinesweeperGame): ScreenAdapter() {
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        game.viewport.update(width, height, true)
    }
}