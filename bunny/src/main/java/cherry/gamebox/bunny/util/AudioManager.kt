package cherry.gamebox.bunny.util

import cherry.gamebox.bunny.util.GamePreferences.volMusic
import cherry.gamebox.bunny.util.GamePreferences.volSound
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound

object AudioManager {

    private var playingMusic: Music? = null

    fun play(sound: Sound, volume: Float = 1f, pitch: Float = 1f, pan: Float = 0f) {
        if (!GamePreferences.sound) return
        sound.play(volSound * volume, pitch, pan)
    }

    fun play(music: Music) {
        playingMusic = music
        if (GamePreferences.music) {
            music.isLooping = true
            music.volume = volMusic
            music.play()
        }
    }

    fun stopMusic() {
        playingMusic?.stop()
    }

    fun getPlayingMusic(): Music? {
        return playingMusic
    }

    fun onSettingsUpdated() {
        playingMusic?.apply {
            volume = volMusic
            if (GamePreferences.music) {
                if (!isPlaying) play()
            } else {
                playingMusic?.pause()
            }
        }

    }
}