package cherry.gamebox.puzzles.notcross

import cherry.gamebox.puzzles.screens.SCREEN_HEIGHT
import cherry.gamebox.puzzles.screens.SCREEN_WIDTH
import com.badlogic.gdx.scenes.scene2d.Group

/**
 * GameBoard
 *
 * @author john
 * @since 2021-12-02
 */
class GameBoard:  Group() {
    init {
        setPosition(SCREEN_WIDTH/2, SCREEN_HEIGHT/2)
    }
}