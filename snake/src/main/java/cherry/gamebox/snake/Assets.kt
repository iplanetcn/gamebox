package cherry.gamebox.snake

import cherry.gamebox.core.GameLogger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Disposable


/**
 * Assets
 *
 * @author john
 * @since 2021-11-17
 */
// private constants
private const val TEXTURE_ATLAS_OBJECTS = "graphics/items.atlas"

object Assets : Disposable, AssetErrorListener {
    private val assetManager: AssetManager by lazy { AssetManager() }

    var backgrounds: AssetBackgrounds
    var blocks: AssetBlocks
    var sounds: AssetSounds
    var musics: AssetMusics
    var fonts: AssetFonts
    val blockWidth: Int
    val blockHeight: Int

    init {
        assetManager.apply {
            setErrorListener(this@Assets)
            load(TEXTURE_ATLAS_OBJECTS, TextureAtlas::class.java)
            finishLoading()
            assetNames.forEach { GameLogger.debug(it) }
            assetManager.get(TEXTURE_ATLAS_OBJECTS, TextureAtlas::class.java).let {
                backgrounds = AssetBackgrounds(it)
                blocks = AssetBlocks(it)
            }
        }

        blockWidth = blocks.blue.regionWidth
        blockHeight = blocks.blue.regionHeight

        sounds = AssetSounds()
        musics = AssetMusics()
        fonts = AssetFonts()
    }

    fun load() {
        GameLogger.log("Asset load()")
        Settings.load()
        musics.themeMusic.volume = .1f
        playMusic()
    }

    fun playMusic() {
        if (Settings.isMusicOn) {
            musics.themeMusic.play()
        }
    }

    fun pauseMusic() {
        musics.themeMusic.pause()
    }

    fun stopMusic() {
        musics.themeMusic.stop()
    }

    fun playSoundBomb() {
        sounds.bombSound.play()
    }

    fun playSoundDrop() {
        sounds.dropSound.play()
    }

    fun playSoundGameOver() {
        sounds.gameOverSound.play()
    }

    fun playSoundLevelUp() {
        sounds.levelUpSound.play()
    }


    override fun dispose() {
        assetManager.dispose()
        sounds.apply {
            bombSound.dispose()
            dropSound.dispose()
            gameOverSound.dispose()
            levelUpSound.dispose()
        }

        musics.apply {
            themeMusic.dispose()
        }
    }

    override fun error(asset: AssetDescriptor<*>?, throwable: Throwable?) {
        GameLogger.error(throwable?.message)
    }
}

//region Asset Parts
class AssetBlocks(atlas: TextureAtlas) {
    val blue: TextureAtlas.AtlasRegion = atlas.findRegion("blue")
    val orange: TextureAtlas.AtlasRegion = atlas.findRegion("orange")
    val purple: TextureAtlas.AtlasRegion = atlas.findRegion("purple")
    val red: TextureAtlas.AtlasRegion = atlas.findRegion("red")
    val teal: TextureAtlas.AtlasRegion = atlas.findRegion("teal")
    val yellow: TextureAtlas.AtlasRegion = atlas.findRegion("yellow")
}

class AssetBackgrounds(atlas: TextureAtlas) {
    val background: TextureAtlas.AtlasRegion = atlas.findRegion("background")
    val gameBoard: TextureAtlas.AtlasRegion = atlas.findRegion("game_board")
}

class AssetMusics {
    val themeMusic: Music = Gdx.audio.newMusic(Gdx.files.internal("music/theme.mp3"))
}

class AssetSounds {
    val bombSound: Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bomb.mp3"))
    val dropSound: Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/drop.mp3"))
    val gameOverSound: Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/game_over.mp3"))
    val levelUpSound: Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/level_up.mp3"))
}

class AssetFonts {
    val fontSmall: BitmapFont = BitmapFont(
        Gdx.files.internal("font/bmf1.fnt"),
        Gdx.files.internal("font/bmf1.png"),
        false
    )

    val fontNormal: BitmapFont = BitmapFont(
        Gdx.files.internal("font/bmf1.fnt"),
        Gdx.files.internal("font/bmf1.png"),
        false
    )

    val fontLarge: BitmapFont = BitmapFont(
        Gdx.files.internal("font/bmf1.fnt"),
        Gdx.files.internal("font/bmf1.png"),
        false
    )

    init {
        fontSmall.data.scale(.5f)
        fontNormal.data.scale(1f)
        fontLarge.data.scale(2f)
    }

}
//endregion