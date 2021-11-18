package cherry.gamebox.tetris.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable

/**
 * Assets
 *
 * @author john
 * @since 2021-11-17
 */

// Texture


// Particle Effects

class Assets : Disposable {
    // assetSet to disposable
    private val assetSet: MutableSet<Disposable> = LinkedHashSet()

    val manager: AssetManager = AssetManager()
    lateinit var batch: SpriteBatch

    // sound
    lateinit var bombSound: Sound
    lateinit var dropSound: Sound
    lateinit var gameOverSound: Sound
    lateinit var levelUpSound: Sound
    lateinit var themeSong: Sound

    // texture
    lateinit var backgroundTexture: Texture
    lateinit var gameBoardTexture: Texture
    lateinit var blueTexture: Texture
    lateinit var orangeTexture: Texture
    lateinit var purpleTexture: Texture
    lateinit var redTexture: Texture
    lateinit var tealTexture: Texture
    lateinit var yellowTexture: Texture


    fun load() {
        batch = SpriteBatch()
        assetSet.add(batch)
        assetSet.addAll(loadSounds())
        assetSet.addAll(loadTextures())
    }

    private fun loadTextures(): Array<Disposable> {
        backgroundTexture = ETexture.BackgroundTexture.load()
        gameBoardTexture = ETexture.GameBoardTexture.load()
        blueTexture = ETexture.BlueTexture.load()
        orangeTexture = ETexture.OrangeTexture.load()
        purpleTexture = ETexture.PurpleTexture.load()
        redTexture = ETexture.RedTexture.load()
        tealTexture = ETexture.TealTexture.load()
        yellowTexture = ETexture.YellowTexture.load()

        return arrayOf(
            backgroundTexture,
            gameBoardTexture,
            blueTexture,
            orangeTexture,
            purpleTexture,
            redTexture,
            tealTexture,
            yellowTexture
        )
    }

    private fun loadSounds(): Array<Disposable> {
        bombSound = ESound.BombSound.load()
        dropSound = ESound.DropSound.load()
        gameOverSound = ESound.GameOverSound.load()
        levelUpSound = ESound.LevelUpSound.load()
        themeSong = ESound.ThemeSong.load()

        return arrayOf(
            bombSound,
            dropSound,
            gameOverSound,
            levelUpSound,
            themeSong
        )
    }

    override fun dispose() {
        assetSet.forEach {
            it.dispose()
        }

        assetSet.clear()
    }
}