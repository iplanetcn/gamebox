package cherry.gamebox.tetris.assets

import com.badlogic.gdx.graphics.g2d.TextureAtlas

/**
 * AssetBlocks
 *
 * @author john
 * @since 2021-11-18
 */
class AssetBlocks(atlas: TextureAtlas) {
    val blue: TextureAtlas.AtlasRegion = atlas.findRegion("blue")
    val orange: TextureAtlas.AtlasRegion = atlas.findRegion("orange")
    val purple: TextureAtlas.AtlasRegion = atlas.findRegion("purple")
    val red: TextureAtlas.AtlasRegion = atlas.findRegion("red")
    val teal: TextureAtlas.AtlasRegion = atlas.findRegion("teal")
    val yellow: TextureAtlas.AtlasRegion = atlas.findRegion("yellow")
}