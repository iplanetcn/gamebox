package cherry.gamebox.tilematch

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Image

class Tile(private val texture: TextureAtlas. AtlasRegion) : Image(texture) {
    var isClicked = false

    fun isSame(other: Tile): Boolean {
        return texture == other.texture
    }
}