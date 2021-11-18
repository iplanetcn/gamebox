package cherry.gamebox.tetris.assets

import com.badlogic.gdx.graphics.g2d.TextureAtlas

/**
 * AssetBackgrounds
 *
 * @author john
 * @since 2021-11-18
 */
class AssetBackgrounds(atlas: TextureAtlas) {
    val background: TextureAtlas.AtlasRegion = atlas.findRegion("background")
    val gameBoard: TextureAtlas.AtlasRegion = atlas.findRegion("game_board")
}