package cherry.gamebox.tilematch

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Disposable

/**
 * Assets
 *
 * @author john
 * @since 2020-12-24
 */
object Assets : Disposable {
    // font
    lateinit var font1: BitmapFont
    lateinit var font2: BitmapFont

    // sound
    lateinit var soundBoost: Sound
    lateinit var soundFailed: Sound
    lateinit var soundMatch: Sound
    lateinit var soundSound: Sound
    lateinit var soundVictory: Sound

    // music
    lateinit var musicBackground: Music

    // image
    lateinit var textureAtlasFrames: TextureAtlas
    lateinit var textureAtlasItems: TextureAtlas
    lateinit var textureAtlasUI: TextureAtlas
    lateinit var textureAtlasCake: TextureAtlas
    lateinit var textureAtlasFruit: TextureAtlas
    lateinit var textureAtlasAnimal: TextureAtlas
    lateinit var textureAtlasGift: TextureAtlas

    lateinit var atlasRegionCakeList: ArrayList<TextureAtlas.AtlasRegion>
    lateinit var atlasRegionFruitList: ArrayList<TextureAtlas.AtlasRegion>
    lateinit var atlasRegionAnimalList: ArrayList<TextureAtlas.AtlasRegion>
    lateinit var atlasRegionGiftList: ArrayList<TextureAtlas.AtlasRegion>


    fun load() {
        font1 = BitmapFont(
            Gdx.files.internal("font/bmf1.fnt"),
            Gdx.files.internal("font/bmf1.png"),
            false
        )

        font2 = BitmapFont(
            Gdx.files.internal("font/bmf2.fnt"),
            Gdx.files.internal("font/bmf2.png"),
            false
        )
        font1.data.setScale(1f)
        font1.color = Color.ORANGE
        font2.data.setScale(1f)
        soundBoost = Gdx.audio.newSound(Gdx.files.internal("audio/boost.wav"))
        soundFailed = Gdx.audio.newSound(Gdx.files.internal("audio/failed.wav"))
        soundMatch = Gdx.audio.newSound(Gdx.files.internal("audio/match.wav"))
        soundSound = Gdx.audio.newSound(Gdx.files.internal("audio/sound.wav"))
        soundVictory = Gdx.audio.newSound(Gdx.files.internal("audio/victory.wav"))

        musicBackground = Gdx.audio.newMusic(Gdx.files.internal("audio/music.mp3"))
        musicBackground.isLooping = true
        musicBackground.volume = 0.5f

        textureAtlasFrames = TextureAtlas("graphic/frames.pack")
        textureAtlasItems = TextureAtlas("graphic/items.pack")
        textureAtlasUI = TextureAtlas("graphic/ui.pack")
        textureAtlasCake = TextureAtlas("package/pack1.pack")
        textureAtlasFruit = TextureAtlas("package/pack2.pack")
        textureAtlasAnimal = TextureAtlas("package/pack3.pack")
        textureAtlasGift = TextureAtlas("package/pack4.pack")

        atlasRegionCakeList = ArrayList()
        atlasRegionFruitList = ArrayList()
        atlasRegionAnimalList = ArrayList()
        atlasRegionGiftList = ArrayList()

        for (i in 1..64) {
            atlasRegionCakeList.add(textureAtlasCake.findRegion("set$i"))
            atlasRegionFruitList.add(textureAtlasFruit.findRegion("set$i"))
            atlasRegionAnimalList.add(textureAtlasAnimal.findRegion("set$i"))
            atlasRegionGiftList.add(textureAtlasGift.findRegion("set$i"))
        }
    }

    override fun dispose() {
        font1.dispose()
        font2.dispose()
        soundBoost.dispose()
        soundFailed.dispose()
        soundMatch.dispose()
        soundSound.dispose()
        soundVictory.dispose()
        musicBackground.dispose()
        textureAtlasFrames.dispose()
        textureAtlasItems.dispose()
        textureAtlasUI.dispose()
        textureAtlasCake.dispose()
        textureAtlasFruit.dispose()
        textureAtlasAnimal.dispose()
        textureAtlasGift.dispose()
    }
}