package cherry.gamebox.tetris.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound

/**
 * Sound
 *
 * @author john
 * @since 2021-11-17
 */
enum class ESound(private val fileName: String) {
    BombSound("sounds/bomb.mp3"),
    DropSound("sounds/drop.mp3"),
    GameOverSound("sounds/game_over.mp3"),
    LevelUpSound("sounds/level_up.mp3"),
    ThemeSong("music/theme.mp3");

    fun load() : Sound {
        return Gdx.audio.newSound(Gdx.files.internal(fileName))
    }
}