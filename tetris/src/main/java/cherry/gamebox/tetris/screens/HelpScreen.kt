package cherry.gamebox.tetris.screens

import cherry.gamebox.tetris.TetrisGame
import cherry.gamebox.tetris.assets.Assets

/**
 * HelpScreen
 *
 * @author john
 * @since 2021-11-19
 */
class HelpScreen(game: TetrisGame) : BaseScreen(game) {

    override fun draw(delta: Float) {
        batcher.begin()
        batcher.draw(Assets.backgrounds.background, 0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT)
        batcher.end()
    }

    override fun update(delta: Float) {
        TODO("Not yet implemented")
    }
}