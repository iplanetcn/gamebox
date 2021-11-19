package cherry.gamebox.bunny

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Disposable


/**
 * Assets
 *
 * @author john
 * @since 2021-11-18
 */
object Assets : Disposable, AssetErrorListener {
    private val assetManager: AssetManager by lazy { AssetManager() }

    var bunny: AssetBunny
    var rock: AssetRock
    var goldCoin: AssetGoldCoin
    var levelDecoration: AssetLevelDecoration
    var feather: AssetFeather
    var fonts: AssetFonts

    init {
        assetManager.apply {
            setErrorListener(this@Assets)
            load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas::class.java)
            finishLoading()
            GameLogger.debug("${assetNames.size} of assets loaded: ")
            assetNames.forEach(GameLogger::debug)
            val atlas = get<TextureAtlas>(Constants.TEXTURE_ATLAS_OBJECTS)
            atlas.textures.forEach { it.setFilter(TextureFilter.Linear, TextureFilter.Linear) }
            fonts = AssetFonts()
            bunny = AssetBunny(atlas)
            goldCoin = AssetGoldCoin(atlas)
            feather = AssetFeather(atlas)
            levelDecoration = AssetLevelDecoration(atlas)
            rock = AssetRock(atlas)
        }

    }

    override fun dispose() {
        assetManager.dispose()
        fonts.apply {
            defaultSmall.dispose()
            defaultNormal.dispose()
            defaultBig.dispose()

        }
    }

    override fun error(asset: AssetDescriptor<*>, throwable: Throwable) {
        GameLogger.error("could loader\", (Exception) throwable);")
    }
}

class AssetBunny(atlas: TextureAtlas) {
    val head = atlas.findRegion("bunny_head")
}

class AssetRock(atlas: TextureAtlas) {
    val edge = atlas.findRegion("rock_edge")
    val middle = atlas.findRegion("rock_middle")
}

class AssetGoldCoin(atlas: TextureAtlas) {
    val goldCoin = atlas.findRegion("goldCoin")
}

class AssetLevelDecoration(atlas: TextureAtlas) {
    val cloud01 = atlas.findRegion("cloud01")
    val cloud02 = atlas.findRegion("cloud02")
    val cloud03 = atlas.findRegion("cloud03")
    val mountainRight = atlas.findRegion("mountain_right")
    val mountainLeft = atlas.findRegion("mountain_left")
    val waterOverlay = atlas.findRegion("water_overlay")
}

class AssetFeather(atlas: TextureAtlas) {
    val feather = atlas.findRegion("feather")
}

class AssetFonts() {
    val defaultSmall = BitmapFont(
        Gdx.files.internal("images/arial-15.fnt"), true
    )
    val defaultNormal = BitmapFont(
        Gdx.files.internal("images/arial-15.fnt"), true
    )
    val defaultBig = BitmapFont(
        Gdx.files.internal("images/arial-15.fnt"), true
    )

    init {
        defaultSmall.data.scale(0.75f)
        defaultNormal.data.scale(1.0f)
        defaultBig.data.scale(2.0f)

        defaultSmall.region.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
        defaultNormal.region.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
        defaultBig.region.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
    }
}