package cherry.gamebox.tetris.assets

import cherry.gamebox.tetris.utils.Constants
import cherry.gamebox.tetris.utils.GameLogger
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Disposable


/**
 * Assets
 *
 * @author john
 * @since 2021-11-17
 */
@Suppress("GDXKotlinStaticResource")
object Assets : Disposable, AssetErrorListener {
    var backgrounds: AssetBackgrounds? = null
    var blocks: AssetBlocks? = null

    private var assetManager: AssetManager? = null

    fun load(assetManager: AssetManager) {
        this.assetManager = assetManager
        this.assetManager?.apply {
            setErrorListener(this@Assets)
            load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas::class.java)
            finishLoading()
            assetNames.forEach { GameLogger.debug(it) }
            assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas::class.java).let {
                backgrounds = AssetBackgrounds(it)
                blocks = AssetBlocks(it)
            }
        }
    }


    override fun dispose() {
        assetManager?.dispose()
    }

    override fun error(asset: AssetDescriptor<*>?, throwable: Throwable?) {
        GameLogger.error(throwable?.message)
    }
}
