package cherry.gamebox.tilematch

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Queue
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport

/**
 * GameScreen
 *
 * @author john
 * @since 2022-09-19
 */
class GameScreen(private val game: TileMatchGame) : ScreenAdapter() {
    private val stage: Stage = Stage(FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT))
    private val heap: MutableList<Tile> = mutableListOf()
    private val stack: MutableList<Tile> = mutableListOf()
    private val queue: Queue<Tile> = Queue<Tile>()
    private val group = Group()

    init {
        Gdx.input.inputProcessor = stage
        spawnActor()
        stage.addActor(group)
    }

    override fun render(delta: Float) {
        // clear
        ScreenUtils.clear(0.294f, 0.294f, 0.294f, 1f)
        game.camera.update()
        // draw
        game.batch.begin()
        game.batch.end()
        stage.act(delta)
        stage.draw()

    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    private fun newTile(texture: AtlasRegion): Tile {
        val tile = Tile(texture)
        tile.setBounds(0f, 0f, 40f, 40f)
        tile.setPosition(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, Align.center)
        tile.setOrigin(tile.width / 2, tile.height / 2)
        return tile
    }

    private fun spawnActor() {
        Assets.atlasRegionFruitList.forEach { texture ->
            heap.add(newTile(texture))
            heap.add(newTile(texture))
            heap.add(newTile(texture))
        }

        var i = 0
        var j = 0
        heap.forEach { tile ->
            if (i == 8) {
                i = 0
                j++
            }
            tile.setPosition(i * 60f, j * 60f)
            tile.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    val tile = event?.listenerActor
                    (tile as? Tile)?.apply {
                        if (tile.isClicked) {
                            return
                        }
                        tile.isClicked = true
                        tile.addAction(
                            Actions.sequence(
                                Actions.parallel(
//                                    Actions.rotateTo(360f, 0.5f, Interpolation.bounceOut),
                                    Actions.moveTo(group.x + (stack.size + queue.size) * 60f, 100 - group.y, 0.3f),
                                ),
                                Actions.delay(0.2f),
                                Actions.run {
                                    stack.add(queue.removeLast())
                                    checkMatches()
                                }
                            )
                        )
                        heap.remove(tile)
                        queue.addFirst(tile)
                    }
                }
            })
            group.addActor(tile)
            i++
        }

        group.setPosition(SCREEN_WIDTH / 2 - 240, SCREEN_HEIGHT - 480)
    }

    fun checkMatches() {
        rangeStack()
        if (stack.size < 3) {
            return
        }

        if (stack.size > 8) {
            game.aoi.toast("Game Over!")
            return
        }

        val first = stack[stack.size - 1]
        val second = stack[stack.size - 2]
        val third = stack[stack.size - 3]
        if (first.isSame(second) && first.isSame(third)) {
            stack.remove(first)
            stack.remove(second)
            stack.remove(third)
            first.addAction(Actions.removeActor())
            second.addAction(Actions.removeActor())
            third.addAction(Actions.removeActor())

            rangeStack()
        }

        if (stack.size == 8) {
            game.aoi.toast("Game Over!")
            return
        }
    }

    fun rangeStack() {
        for ((index, tile) in stack.withIndex()) {
            if (tile.x != group.x + index * 60f) {
                tile.addAction(Actions.moveTo(group.x + index * 60f, 100 - group.y, 0.2f))
            }
        }
    }
}