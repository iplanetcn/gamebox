package cherry.gamebox.bunny.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.math.MathUtils


/**
 * GamePreferences
 *
 * @author john
 * @since 2022-09-21
 */
object GamePreferences {
    var sound = false
    var music = false
    var volSound = 0f
    var volMusic = 0f
    var charSkin = 0
    var showFpsCounter = false
    private val prefs: Preferences = Gdx.app.getPreferences(Constants.PREFERENCES)

    fun load() {
        sound = prefs.getBoolean("sound", true)
        music = prefs.getBoolean("music", true)
        volSound = MathUtils.clamp(prefs.getFloat("volSound", 0.5f), 0.0f, 1.0f)
        volMusic = MathUtils.clamp(prefs.getFloat("volMusic", 0.5f), 0.0f, 1.0f)
        charSkin = MathUtils.clamp(prefs.getInteger("charSkin", 0), 0, 2)
        showFpsCounter = prefs.getBoolean("showFpsCounter", false)
    }

    fun save() {
        prefs.putBoolean("sound", sound)
        prefs.putBoolean("music", music)
        prefs.putFloat("volSound", volSound)
        prefs.putFloat("volMusic", volMusic)
        prefs.putInteger("charSkin", charSkin)
        prefs.putBoolean("showFpsCounter", showFpsCounter)
        prefs.flush()
    }
}