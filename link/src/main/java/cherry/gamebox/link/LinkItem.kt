package cherry.gamebox.link

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Rectangle

/**
 * LinkItem
 *
 * @author john
 * @since 2020-12-25
 */
class LinkItem : LinkInterface {
    var rowId = -1
    var colId = -1
    private var empty = true
    lateinit var image: TextureAtlas.AtlasRegion
    lateinit var box: Rectangle

    companion object {
        var selectedItem: LinkItem? = null
        var targetItem: LinkItem? = null
    }

    override fun isEmpty(): Boolean {
        return empty
    }

    override fun isNotEmpty(): Boolean {
        return !empty
    }

    override fun setEmpty() {
        empty = true
    }

    override fun setNonEmpty() {
        empty = false
    }

    override fun equals(other: Any?): Boolean {
        return if (other is LinkItem) {
            image.name == other.image.name
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = rowId
        result = 31 * result + colId
        result = 31 * result + empty.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + box.hashCode()
        return result
    }
}