package cherry.gamebox.link

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import javax.microedition.khronos.opengles.GL10

class LinkGame : ApplicationAdapter() {

    private lateinit var items: ArrayList<ArrayList<LinkItem>>
    private lateinit var batch: SpriteBatch
    private lateinit var background: Sprite
    private lateinit var camera: OrthographicCamera
    private lateinit var pos: Vector3
    private lateinit var packSetList: ArrayList<TextureAtlas.AtlasRegion>
    private lateinit var assets: Assets
    private lateinit var shapeRenderer: ShapeRenderer
    private var screenWidth: Float = 0f
    private var screenHeight: Float = 0f
    private val cols = 8
    private val rows = 10
    private var selCol = -1
    private var selRow = -1
    private var isSelected = false
    private var pathList: List<Point>? = null
    private var isWin = false

    override fun create() {
        assets = Assets()
        assets.load()
        batch = SpriteBatch()
        assets.musicBackground.play()
        packSetList = assets.atlasRegionAnimalList
        screenWidth = Gdx.graphics.width.toFloat()
        screenHeight = Gdx.graphics.height.toFloat()

        shapeRenderer = ShapeRenderer()

        background = assets.textureAtlasFrames.createSprite("background")
        background.setPosition(0F, 0F)
        camera = OrthographicCamera()
        camera.setToOrtho(false, screenWidth, screenHeight)

        pos = Vector3.Zero

        val images = ArrayList<TextureAtlas.AtlasRegion>()
        for (i in 0 until (rows -2) * (cols - 2) / 2) {
            val item = packSetList.removeLast()
            images.add(item)
            images.add(item)
        }
        images.shuffle()

        items = ArrayList()
        for (i in 0 until rows) {
            val rowItems = ArrayList<LinkItem>()
            for (j in 0 until cols) {
                val li = LinkItem()
                li.colId = i
                li.rowId = j
                li.box = Rectangle(
                        (screenWidth - cols * 160) / 2 + j * 160f,
                        (screenHeight - rows * 160) / 2 + i * 160f,
                        160f, 160f)
                if (j == 0 || i == 0 || j == cols - 1 || i == rows - 1) {
                    li.image = packSetList[0]
                    li.setEmpty()
                } else {
                    li.image = images.removeFirst()
                    li.setNonEmpty()
                }
                rowItems.add(li)
            }
            items.add(rowItems)
        }
    }

    override fun render() {
        // logic
        camera.update()

        var curLinkItem: LinkItem? = null
        // produce new icon
        if (Gdx.input.justTouched()) {
            pos.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0F)
            camera.unproject(pos)
            // locate selected item
            loop@ for (subItems in items) {
                for (item in subItems) {
                    if (item.box.contains(pos.x, pos.y)) {
                        curLinkItem = item
                        selRow = curLinkItem.rowId
                        selCol = curLinkItem.colId
                        isSelected = true
                        break@loop
                    }
                }
            }

            if (isSelected) {
                LinkItem.targetItem = LinkItem.selectedItem
                LinkItem.selectedItem = curLinkItem

                if (LinkItem.targetItem != null &&
                        LinkItem.selectedItem != null &&
                        LinkItem.targetItem == LinkItem.selectedItem &&
                        !(LinkItem.targetItem === LinkItem.selectedItem) &&
                        LinkItem.targetItem!!.isNotEmpty() &&
                        LinkItem.selectedItem!!.isNotEmpty()) {

                    val srcPt = Point(LinkItem.selectedItem!!.colId, LinkItem.selectedItem!!.rowId)
                    val desPt = Point(LinkItem.targetItem!!.colId, LinkItem.targetItem!!.rowId)

                    pathList = LinkSearch.matchBlockTwo(items, srcPt, desPt)

                    if (pathList != null) {
                        assets.soundMatch.play()
                        LinkItem.targetItem!!.setEmpty()
                        LinkItem.selectedItem!!.setEmpty()

                        LinkItem.targetItem = null
                        LinkItem.selectedItem = null

                        selCol = -1
                        selRow = -1
                    }
                }

            }

            isSelected = false
        }

        // drawing
        Gdx.gl.glClearColor(1F, 1F, 1F, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(background, 0f, 0f, screenWidth, screenHeight)

        for (subItems in items) {
            for (item in subItems) {
                if (item.isNotEmpty()) {
                    // draw outline
                    if (selCol == item.colId && selRow == item.rowId) {
                        batch.draw(
                                assets.textureAtlasItems.findRegion("pat4"),
                                item.box.x - 64f,
                                item.box.y - 64f,
                                item.box.width + 128,
                                item.box.height + 128
                        )
                    }
                    // draw icon
                    batch.draw(
                            item.image,
                            item.box.x + (item.box.width - item.image.regionWidth) / 2,
                            item.box.y + (item.box.height - item.image.regionHeight) / 2
                    )
                }
            }
        }

        batch.end()
//        renderHelper()
    }

    private fun renderHelper() {
        Gdx.gl.glEnable(GL10.GL_BLEND)
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color(0f, 1f, 1f, 0.5f)
        shapeRenderer.rect(screenWidth / 2 - 80, screenHeight / 2 - 80, 160f, 160f)
        shapeRenderer.end()
        Gdx.gl.glDisable(GL10.GL_BLEND)

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.CYAN
        shapeRenderer.line(0f, screenHeight / 2, screenWidth, screenHeight / 2)
        shapeRenderer.end()

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.CYAN
        shapeRenderer.line(screenWidth / 2, 0f, screenWidth / 2, screenHeight)
        shapeRenderer.end()
    }

    private fun renderText() {
        assets.font1.draw(
                batch,
                "screenWidth:$screenWidth, screenHeight:$screenHeight",
                50f,
                100f
        )
        assets.font1.draw(
                batch,
                "icon->originalWidth:${assets.atlasRegionFruitList[0].originalWidth}, originalHeight:${assets.atlasRegionFruitList[0].originalHeight}",
                50f,
                200f
        )
        assets.font1.draw(
                batch,
                "icon->packedWidth:${assets.atlasRegionFruitList[0].packedWidth}, packedHeight:${assets.atlasRegionFruitList[0].packedHeight}",
                50f,
                300f
        )
        assets.font1.draw(
                batch,
                "icon->regionWidth:${assets.atlasRegionFruitList[0].regionWidth}, regionHeight:${assets.atlasRegionFruitList[0].regionHeight}",
                50f,
                400f
        )
    }

    override fun dispose() {
        assets.dispose()
        batch.dispose()
        shapeRenderer.dispose()
    }

}