package cherry.gamebox.superjumper

import android.util.Log.i
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.Disposable

object Assets : Disposable{
    lateinit var sounds: Sounds
    lateinit var musics: Musics
    lateinit var sprites: Sprites
    lateinit var joystick: Joystick

    fun load() {
        sounds = Sounds()
        musics = Musics()
        sprites = Sprites()
        joystick = Joystick()
    }

    override fun dispose() {
        sounds.dispose()
        musics.dispose()
        sprites.dispose()
        joystick.dispose()
    }


    class Sounds {
        val coinSound: Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"))
        val explosionSound: Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"))
        val hurtSound: Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/hurt.wav"))
        val jumpSound: Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"))
        val powerUpSound: Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/power_up.wav"))
        val tapSound: Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/tap.wav"))

        fun dispose() {
            coinSound.dispose()
            explosionSound.dispose()
            hurtSound.dispose()
            jumpSound.dispose()
            powerUpSound.dispose()
            tapSound.dispose()
            joystick.dispose()
        }
    }

    class Musics {
        val themeMusic: Music = Gdx.audio.newMusic(Gdx.files.internal("music/time_for_adventure.mp3"))

        fun dispose() {
            themeMusic.dispose()
        }
    }


    fun playMusic() {
        musics.themeMusic.volume = .1f
        musics.themeMusic.play()
    }

    fun pauseMusic() {
        musics.themeMusic.pause()
    }

    fun stopMusic() {
        musics.themeMusic.stop()
    }

    class Sprites {

//        coin.png
//        fruit.png
//        knight.png
//        platforms.png
//        slime_green.png
//        slime_purple.png
//        world_tileset.png

        private val coinTexture: Texture
        private val fruitTexture: Texture
        private val knightTexture: Texture

        val coinList: MutableList<Sprite> = ArrayList()
        val fruitList: MutableList<Sprite> = ArrayList()
        val knightIdleList: MutableList<Sprite> = ArrayList()
        val knightRunList: MutableList<Sprite> = ArrayList()
        val knightRollList: MutableList<Sprite> = ArrayList()
        val knightHitList: MutableList<Sprite> = ArrayList()
        val knightDeathList: MutableList<Sprite> = ArrayList()

        val platforms: MutableList<Sprite> = ArrayList()
        val slimeGreen: MutableList<Sprite> = ArrayList()
        val slimePurple: MutableList<Sprite> = ArrayList()
        val worldTileset: MutableList<Sprite> = ArrayList()

        init {
            coinTexture = Texture(Gdx.files.internal("sprites/coin.png"))
            for (i in 0 until 192 step 16) {
                coinList.add(Sprite(coinTexture, i, 0, 16, 16))
            }

            fruitTexture = Texture(Gdx.files.internal("sprites/fruit.png"))
            for (i in  0 until 48 step 16) {
                for ( j in 0 until 64 step 16) {
                    fruitList.add(Sprite(fruitTexture, i, j, 16, 16))
                }
            }
            knightTexture = Texture(Gdx.files.internal("sprites/knight.png"))
            for (i in  0 until 32 * 4 step 32) {
                knightIdleList.add(Sprite(knightTexture, i, 0, 32,32))
            }
            for (i in  0 until 32 * 8 step 32) {
                for (j in 32 * 2 until 64 + 32 * 2 step 32) {
                    knightRunList.add(Sprite(knightTexture, i, j, 32, 32))
                }
            }

            for (i in  0 until 32 * 4 step 32) {
                knightRollList.add(Sprite(knightTexture, i, 32 * 5, 32, 32))
            }

            for (i in  0 until 32 * 8 step 32) {
                knightHitList.add(Sprite(knightTexture, i, 32 * 6, 32, 32))
            }

            for (i in  0 until 32 * 8 step 32) {
                knightDeathList.add(Sprite(knightTexture, i, 32 * 7, 32, 32))
            }
        }

        fun dispose() {
            coinTexture.dispose()
            fruitTexture.dispose()
        }
    }

    class Joystick {
        val backgroundTexture: Texture
        val knobTexture: Texture

        init {
            backgroundTexture = Texture(Gdx.files.internal("textures/joystick_background.png"))
            knobTexture = Texture(Gdx.files.internal("textures/joystick_knob.png"))
        }

        fun dispose() {
            backgroundTexture.dispose()
            knobTexture.dispose()
        }
    }
}