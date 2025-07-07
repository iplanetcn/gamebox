package cherry.gamebox.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.utils.Disposable


/**
 * Assets
 *
 * @author john
 * @since 2021-11-17
 */
// private constants
private const val TEXTURE_ATLAS_OBJECTS = "graphics/items.atlas"
private const val TEXTURE_ATLAS_CARDS = "cards/cards.atlas"
private const val TEXTURE_ATLAS_UI = "graphic/ui.pack"

object CoreAssets : Disposable, AssetErrorListener {
    private val assetManager: AssetManager by lazy { AssetManager() }

    var backgrounds: AssetBackgrounds
    var blocks: AssetBlocks
    var cards: AssetCards
    var ui: AssetUI
    var sounds: AssetSounds
    var musics: AssetMusics
    var fonts: AssetFonts
    val blockWidth: Int
    val blockHeight: Int

    init {
        assetManager.apply {
            setErrorListener(this@CoreAssets)
            load(TEXTURE_ATLAS_OBJECTS, TextureAtlas::class.java)
            load(TEXTURE_ATLAS_CARDS, TextureAtlas::class.java)
            load(TEXTURE_ATLAS_UI, TextureAtlas::class.java)
            finishLoading()
            assetNames.forEach { GameLogger.debug(it) }
            assetManager.get(TEXTURE_ATLAS_OBJECTS, TextureAtlas::class.java).let {
                backgrounds = AssetBackgrounds(it)
                blocks = AssetBlocks(it)
            }
            assetManager.get(TEXTURE_ATLAS_CARDS, TextureAtlas::class.java).let {
                cards = AssetCards(it)
            }
            assetManager.get(TEXTURE_ATLAS_UI, TextureAtlas::class.java).let {
                ui = AssetUI(it)
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
        sounds.dispose()
        musics.dispose()
        fonts.dispose()
    }

    override fun error(asset: AssetDescriptor<*>?, throwable: Throwable?) {
        GameLogger.error(throwable?.message)
    }
}

//region Asset Parts
class AssetCards(atlas: TextureAtlas) {
    val spades: MutableList<Sprite> = ArrayList()
    val clubs: MutableList<Sprite> = ArrayList()
    val hearts: MutableList<Sprite> = ArrayList()
    val diamonds: MutableList<Sprite> = ArrayList()
    val backs: MutableList<Sprite> = ArrayList()

    init {
        for (i in 1..13) {
            val s = when(i) {
                1 -> "A"
                2,3,4,5,6,7,8,9,10-> "$i"
                11 -> "J"
                12 -> "Q"
                13 -> "K"
                else -> "CardBack"
            }

            spades.add(atlas.createSprite("Spade$s"))
            clubs.add(atlas.createSprite("Club$s"))
            hearts.add(atlas.createSprite("Heart$s"))
            diamonds.add(atlas.createSprite("Diamond$s"))
        }

        backs.add(atlas.createSprite("CardBack"))
    }
}

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

class AssetUI(atlas: TextureAtlas) {
    val btShuffle: TextureAtlas.AtlasRegion = atlas.findRegion("btshuffle")
}

class AssetMusics : Disposable {
    val themeMusic: Music = Gdx.audio.newMusic(Gdx.files.internal("music/theme.mp3"))

    override fun dispose() {
        themeMusic.dispose()
    }
}

class AssetSounds : Disposable {
    val bombSound: Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bomb.mp3"))
    val dropSound: Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/drop.mp3"))
    val gameOverSound: Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/game_over.mp3"))
    val levelUpSound: Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/level_up.mp3"))

    override fun dispose() {
        bombSound.dispose()
        dropSound.dispose()
        gameOverSound.dispose()
        levelUpSound.dispose()
    }
}

class AssetFonts : Disposable {
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

    var font48: BitmapFont
    var font16: BitmapFont

    init {
        fontSmall.data.scale(.5f)
        fontNormal.data.scale(1f)
        fontLarge.data.scale(2f)

        val generator = FreeTypeFontGenerator(Gdx.files.internal("roboto.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.borderWidth = 1.5f
        parameter.size = 48
        parameter.color = Color.BLACK
        parameter.borderColor = Color.BLACK
        font48 = generator.generateFont(parameter)
        parameter.color = Color.BLACK
        parameter.borderColor = Color.WHITE
        parameter.borderWidth = 0.7f
        parameter.size = 16
        font16 = generator.generateFont(parameter)
        generator.dispose()
        font48.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        font16.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

    }

    override fun dispose() {
        fontSmall.dispose()
        fontNormal.dispose()
        fontLarge.dispose()
        font16.dispose()
        font48.dispose()
    }

}
//endregion