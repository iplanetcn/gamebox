package cherry.gamebox.tetris.assets

import com.badlogic.gdx.graphics.Texture

/**
 * ETexture
 *
 * @author john
 * @since 2021-11-17
 */
enum class ETexture(private val fileName: String) {
    BackgroundTexture("images/background.png"),
    GameBoardTexture("images/game_board.png"),
    BlueTexture("images/blue.png"),
    OrangeTexture("images/orange.png"),
    PurpleTexture("images/purple.png"),
    RedTexture("images/red.png"),
    TealTexture("images/teal.png"),
    YellowTexture("images/yellow.png");

    fun load(): Texture {
        return Texture(fileName)
    }
}