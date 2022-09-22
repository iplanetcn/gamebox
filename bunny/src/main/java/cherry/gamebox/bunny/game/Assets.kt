package cherry.gamebox.bunny.game

import cherry.gamebox.bunny.util.Constants
import cherry.gamebox.core.GameLogger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array
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
    var feather: AssetFeather
    var levelDecoration: AssetLevelDecoration
    var music: AssetMusic
    var sounds: AssetSounds
    var fonts: AssetFonts
    var book: AssetBook

    init {
        assetManager.apply {
            // set asset manager error handler
            assetManager.setErrorListener(this@Assets)
            // load texture atlas
            assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas::class.java)
            assetManager.load(Constants.TEXTURE_ATLAS_UI, TextureAtlas::class.java)
            // load sounds
            assetManager.load("sounds/jump.wav", Sound::class.java)
            assetManager.load("sounds/jump_with_feather.wav", Sound::class.java)
            assetManager.load("sounds/pickup_coin.wav", Sound::class.java)
            assetManager.load("sounds/pickup_feather.wav", Sound::class.java)
            assetManager.load("sounds/live_lost.wav", Sound::class.java)
            // load music
            assetManager.load("music/keith303_-_brand_new_highscore.mp3", Music::class.java)

            // start loading assets and wait until finished
            assetManager.finishLoading()
            GameLogger.debug("# of assets loaded: ${assetManager.assetNames.size}")
            for (a in assetManager.assetNames) {
                GameLogger.debug("asset: $a")
            }

            val atlas: TextureAtlas = assetManager[Constants.TEXTURE_ATLAS_OBJECTS]
            val atlasUI: TextureAtlas = assetManager[Constants.TEXTURE_ATLAS_UI]

            // enable texture filtering for pixel smoothing
            for (t in atlas.textures) {
                t.setFilter(TextureFilter.Linear, TextureFilter.Linear)
            }

            // create game resource objects
            bunny = AssetBunny(atlas)
            fonts = AssetFonts()
            rock = AssetRock(atlas)
            goldCoin = AssetGoldCoin(atlas)
            feather = AssetFeather(atlas)
            levelDecoration = AssetLevelDecoration(atlas)
            music = AssetMusic(assetManager)
            sounds = AssetSounds(assetManager)
            book = AssetBook(atlas, atlasUI)
        }
    }

    fun load() {
        GameLogger.log("Asset load()")
    }

    class AssetBunny(atlas: TextureAtlas) {
        val head: AtlasRegion

        val animNormal: Animation<TextureRegion>
        val animCopterTransform: Animation<TextureRegion>
        val animCopterTransformBack: Animation<TextureRegion>
        val animCopterRotate: Animation<TextureRegion>
        init {
            head = atlas.findRegion("bunny_head")
            // Animation: Bunny Normal
            var regions: Array<AtlasRegion> = atlas.findRegions("anim_bunny_normal")
            animNormal = Animation<TextureRegion>(
                1.0f / 10.0f, regions,
                Animation.PlayMode.LOOP_PINGPONG
            )


            // Animation: Bunny Copter - knot ears
            regions = atlas.findRegions("anim_bunny_copter")
            animCopterTransform = Animation<TextureRegion>(1.0f / 10.0f, regions)

            // Animation: Bunny Copter - unknot ears
            regions = atlas.findRegions("anim_bunny_copter")
            animCopterTransformBack = Animation<TextureRegion>(
                1.0f / 10.0f, regions,
                Animation.PlayMode.REVERSED
            )

            // Animation: Bunny Copter - rotate ears
            regions = Array()
            regions.add(atlas.findRegion("anim_bunny_copter", 4))
            regions.add(atlas.findRegion("anim_bunny_copter", 5))
            animCopterRotate = Animation<TextureRegion>(1.0f / 15.0f, regions)
        }
    }

    class AssetRock(atlas: TextureAtlas) {
        val edge: AtlasRegion
        val middle: AtlasRegion

        init {
            edge = atlas.findRegion("rock_edge")
            middle = atlas.findRegion("rock_middle")
        }
    }

    class AssetGoldCoin(atlas: TextureAtlas) {
        val goldCoin: AtlasRegion

        init {
            goldCoin = atlas.findRegion("item_gold_coin")
        }
    }

    class AssetFeather(atlas: TextureAtlas) {
        val feather: AtlasRegion

        init {
            feather = atlas.findRegion("item_feather")
        }
    }

    class AssetLevelDecoration(atlas: TextureAtlas) {
        val cloud01: AtlasRegion
        val cloud02: AtlasRegion
        val cloud03: AtlasRegion
        val mountainLeft: AtlasRegion
        val mountainRight: AtlasRegion
        val waterOverlay: AtlasRegion
        val carrot: AtlasRegion
        val goal: AtlasRegion

        init {
            cloud01 = atlas.findRegion("cloud01")
            cloud02 = atlas.findRegion("cloud02")
            cloud03 = atlas.findRegion("cloud03")
            mountainLeft = atlas.findRegion("mountain_left")
            mountainRight = atlas.findRegion("mountain_right")
            waterOverlay = atlas.findRegion("water_overlay")
            carrot = atlas.findRegion("carrot")
            goal = atlas.findRegion("goal")
        }
    }


    class AssetFonts {
        val defaultSmall: BitmapFont
        val defaultNormal: BitmapFont
        val defaultBig: BitmapFont

        init {
            // create three fonts using Libgdx's 15px bitmap font
            defaultSmall = BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true)
            defaultNormal = BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true)
            defaultBig = BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true)
            GameLogger.debug("Fonts loaded")

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


    class AssetSounds(am: AssetManager) {
        val jump: Sound
        val jumpWithFeather: Sound
        val pickupCoin: Sound
        val pickupFeather: Sound
        val liveLost: Sound

        init {
            jump = am.get("sounds/jump.wav", Sound::class.java)
            jumpWithFeather = am.get("sounds/jump_with_feather.wav", Sound::class.java)
            pickupCoin = am.get("sounds/pickup_coin.wav", Sound::class.java)
            pickupFeather = am.get("sounds/pickup_feather.wav", Sound::class.java)
            liveLost = am.get("sounds/live_lost.wav", Sound::class.java)
        }
    }

    class AssetMusic(am: AssetManager) {
        val song01: Music

        init {
            song01 = am.get("music/keith303_-_brand_new_highscore.mp3", Music::class.java)
        }
    }

    class AssetBook(atlas: TextureAtlas, atlasUI: TextureAtlas) {
        var cover: AtlasRegion
        var animHelpIdle: Animation<TextureRegion>
        var animHelpTilt: Animation<TextureRegion>
        var animHelpTouch: Animation<TextureRegion>
        var animHelpFly: Animation<TextureRegion>

        init {
            cover = atlasUI.findRegion("book-cover")
            // Animation: Help Idle
            animHelpIdle = Animation(1.0f / 1.0f, atlas.findRegions("help/normal"))

            // Animation: Help Tilt
            var regions: Array<AtlasRegion?> = atlas.findRegions("help/anim_tilt")
            regions.insert(2, regions[0])
            animHelpTilt = Animation(1.0f / 2.0f, regions, Animation.PlayMode.LOOP)

            // Animation: Help Tilt
            regions = atlas.findRegions("help/anim_touch")
            animHelpTouch = Animation(1.0f / 5.0f, regions, Animation.PlayMode.LOOP_PINGPONG)

            // Animation: Help Fly
            regions = atlas.findRegions("help/anim_fly")
            animHelpFly = Animation(1.0f / 20.0f, regions, Animation.PlayMode.LOOP_PINGPONG)
        }
    }


    override fun dispose() {
        assetManager.dispose()
        fonts.defaultBig.dispose()
        fonts.defaultNormal.dispose()
        fonts.defaultSmall.dispose()
    }

    override fun error(asset: AssetDescriptor<*>, throwable: Throwable?) {
        GameLogger.error("Couldn't load asset '${asset.fileName}'$throwable")
    }
}