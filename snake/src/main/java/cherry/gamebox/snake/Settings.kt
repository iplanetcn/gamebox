package cherry.gamebox.snake

import cherry.gamebox.core.GameLogger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences

/**
 * Settings
 *
 * @author john
 * @since 2021-11-19
 */
private const val PREF_NAME = "cherry.gamebox.tetris"
private const val KEY_IS_MUSIC_ON = "isMusicOn"
private const val KEY_IS_SOUND_ON = "isSoundOn"
private const val KEY_BEST_SCORE = "bestScore"

object Settings {
    var isMusicOn: Boolean
    var isSoundOn: Boolean
    var bestScore: Long

    private val pref: Preferences = Gdx.app.getPreferences(PREF_NAME)

    init {
        isMusicOn = pref.getBoolean(KEY_IS_MUSIC_ON, true)
        isSoundOn = pref.getBoolean(KEY_IS_SOUND_ON, true)
        bestScore = pref.getLong(KEY_BEST_SCORE, 0)

    }

    fun load() {
        GameLogger.log("Settings load()")
    }

    fun save() {
        pref.putBoolean(KEY_IS_MUSIC_ON, isMusicOn)
        pref.putBoolean(KEY_IS_SOUND_ON, isSoundOn)
        pref.putLong(KEY_BEST_SCORE, bestScore)
        pref.flush()
    }
}