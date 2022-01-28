package cherry.gamebox.core

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table


/**
 * PagedScollPane
 *
 * @author john
 * @since 2022-01-10
 */
class PagedScrollPane : ScrollPane {
    private var wasPanDragFling = false
    private val pageSpacing = 0f
    private lateinit var content: Table

    constructor() : super(null) {
        setup()
    }

    constructor(skin: Skin?) : super(null, skin) {
        setup()
    }

    constructor(skin: Skin?, styleName: String?) : super(null, skin, styleName) {
        setup()
    }

    constructor(widget: Actor?, style: ScrollPaneStyle?) : super(null, style) {
        setup()
    }

    private fun setup() {
        content = Table()
        content.defaults().space(50f)
        super.setActor(content)
    }

    fun addPages(vararg pages: Actor?) {
        for (page in pages) {
            content.add(page).expandY().fillY()
        }
    }

    fun addPage(page: Actor?) {
        content.add(page).expandY().fillY()
    }

    override fun act(delta: Float) {
        super.act(delta)
        if (wasPanDragFling && !isPanning && !isDragging && !isFlinging) {
            wasPanDragFling = false
            scrollToPage()
        } else {
            if (isPanning || isDragging || isFlinging) {
                wasPanDragFling = true
            }
        }
    }

    override fun setWidget(widget: Actor?) {
        throw UnsupportedOperationException("Use PagedScrollPane#addPage.")
    }

    override fun setWidth(width: Float) {
        super.setWidth(width)
        for (cell in content.cells) {
            cell.width(width)
        }
        content.invalidate()
    }

    fun setPageSpacing(pageSpacing: Float) {
        content.defaults().space(pageSpacing)
        for (cell in content.cells) {
            cell.space(pageSpacing)
        }
        content.invalidate()
    }

    private fun scrollToPage() {
        if (scrollX >= maxX || scrollX <= 0) return
        val pages: Array<Actor> = content.children.items
        var pageX = 0f
        var pageWidth = 0f
        if (pages.isNotEmpty()) {
            for (a in pages) {
                pageX = a.x
                pageWidth = a.width
                if (scrollX < pageX + pageWidth * 0.5) {
                    break
                }
            }
            scrollX = MathUtils.clamp(pageX - (width - pageWidth) / 2.0f, 0f, maxX)
        }
    }
}