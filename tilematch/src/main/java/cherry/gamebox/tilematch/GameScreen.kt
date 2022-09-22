package cherry.gamebox.tilematch

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport

/**
 * GameScreen
 *
 * @author john
 * @since 2022-09-19
 */
class GameScreen(private val game: TileMatchGame) : ScreenAdapter() {
    private var stage: Stage = Stage(FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT))
    private var heap: MutableList<Image> = mutableListOf()
    private var stack: MutableList<Image> = mutableListOf()
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

    private fun spawnActor() {
        Assets.atlasRegionFruitList.forEach { fruitTexture ->
            val img = Image(fruitTexture)
            img.setBounds(0f, 0f, 40f, 40f)
            img.setPosition(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, Align.center)
            img.setOrigin(img.width / 2, img.height / 2)
            heap.add(img)
        }

        var i = 0
        var j = 0
        heap.forEach { image ->
            if (i == 8) {
                i = 0
                j++
            }
            image.setPosition(i * 60f, j * 60f)
            image.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    val actor = event?.listenerActor
                    actor?.addAction(
                        Actions.sequence(
                            Actions.parallel(
                                Actions.rotateTo(720f, 1f, Interpolation.linear),
                                Actions.moveTo(group.x + stack.size * 60f, 100 - group.y, 0.5f)
                            ),
//                            Actions.fadeOut(0.3f),
//                            Actions.removeActor()
                        )
                    )
                    heap.remove(actor)
                    stack.add(actor as Image)
                }
            })
            group.addActor(image)
            i++
        }

        group.setPosition(SCREEN_WIDTH / 2 - 240, SCREEN_HEIGHT - 480)
    }
}