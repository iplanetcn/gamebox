package cherry.gamebox.bunny.game

import cherry.gamebox.bunny.util.Constants
import cherry.gamebox.core.GameLogger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.utils.Disposable


/**
 * Assets
 *
 * @author john
 * @since 2021-11-18
 */
class Assets : Disposable, AssetErrorListener {
    lateinit var assetManager: AssetManager
    lateinit var bunny: AssetBunny
    lateinit var rock: AssetRock
    lateinit var goldCoin: AssetGoldCoin
    lateinit var feather: AssetFeather
    lateinit var levelDecoration: AssetLevelDecoration

    inner class AssetBunny(atlas: TextureAtlas) {
        val head: AtlasRegion

        init {
            head = atlas.findRegion("bunny_head")
        }
    }

    inner class AssetRock(atlas: TextureAtlas) {
        val edge: AtlasRegion
        val middle: AtlasRegion

        init {
            edge = atlas.findRegion("rock_edge")
            middle = atlas.findRegion("rock_middle")
        }
    }

    inner class AssetGoldCoin(atlas: TextureAtlas) {
        val goldCoin: AtlasRegion

        init {
            goldCoin = atlas.findRegion("item_gold_coin")
        }
    }

    inner class AssetFeather(atlas: TextureAtlas) {
        val feather: AtlasRegion

        init {
            feather = atlas.findRegion("item_feather")
        }
    }

    inner class AssetLevelDecoration(atlas: TextureAtlas) {
        val cloud01: AtlasRegion
        val cloud02: AtlasRegion
        val cloud03: AtlasRegion
        val mountainLeft: AtlasRegion
        val mountainRight: AtlasRegion
        val waterOverlay: AtlasRegion

        init {
            cloud01 = atlas.findRegion("cloud01")
            cloud02 = atlas.findRegion("cloud02")
            cloud03 = atlas.findRegion("cloud03")
            mountainLeft = atlas.findRegion("mountain_left")
            mountainRight = atlas.findRegion("mountain_right")
            waterOverlay = atlas.findRegion("water_overlay")
        }
    }

    var fonts: AssetFonts? = null

    inner class AssetFonts {
        val defaultSmall: BitmapFont
        val defaultNormal: BitmapFont
        val defaultBig: BitmapFont

        init {
            // create three fonts using Libgdx's 15px bitmap font
            defaultSmall = BitmapFont(Gdx.files.internal("assets/images/arial-15.fnt"), true)
            defaultNormal = BitmapFont(Gdx.files.internal("assets/images/arial-15.fnt"), true)
            defaultBig = BitmapFont(Gdx.files.internal("assets/images/arial-15.fnt"), true)
            GameLogger.debug( "Fonts loaded")

            // set font sizes
            defaultSmall.data.setScale(0.75f, 0.75f)
            defaultNormal.data.setScale(1.0f, 1.0f)
            defaultBig.data.setScale(2.0f, 2.0f)

            // enable linear texture filtering for smooth fonts
            defaultSmall.region.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
            defaultNormal.region.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
            defaultBig.region.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
        }
    }

    fun init(assetManager: AssetManager) {
        this.assetManager = assetManager
        // set asset manager error handler
        assetManager.setErrorListener(this)

        // load texture atlas
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas::class.java)

        // start loading assets and wait until finished
        assetManager.finishLoading()
        GameLogger.debug("# of assets loaded: " + assetManager.assetNames.size)
        for (a in assetManager.assetNames) {
            GameLogger.debug("asset: $a")
        }
        val atlas: TextureAtlas = assetManager[Constants.TEXTURE_ATLAS_OBJECTS]

        // enable texture filtering for pixel smoothing
        for (t in atlas.textures) {
            t.setFilter(TextureFilter.Linear, TextureFilter.Linear)
        }

        // create game resource objects
        bunny = AssetBunny(atlas)
        rock = AssetRock(atlas)
        goldCoin = AssetGoldCoin(atlas)
        feather = AssetFeather(atlas)
        levelDecoration = AssetLevelDecoration(atlas)

        // fonts
        fonts = AssetFonts()
    }

    override fun dispose() {
        assetManager.dispose()
        fonts!!.defaultBig.dispose()
        fonts!!.defaultNormal.dispose()
        fonts!!.defaultSmall.dispose()
    }

    override fun error(asset: AssetDescriptor<*>, throwable: Throwable?) {
        GameLogger.error("Couldn't load asset '${asset.fileName}'$throwable")
    }

    companion object {
        val instance = Assets()
    }
}