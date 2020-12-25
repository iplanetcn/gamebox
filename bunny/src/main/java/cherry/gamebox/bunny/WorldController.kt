package cherry.gamebox.bunny

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import kotlin.random.Random


/**
 * WorldController
 *
 * @author john
 * @since 2020-12-09
 */
class WorldController {
    lateinit var testSprites: Array<Sprite>
    private var selectedSprite: Int = 0


    companion object {
        val TAG = WorldController::class.java.simpleName
    }

    init {
        init()
    }

    fun init() {
        initTestObjects()
    }

    private fun initTestObjects() {
        testSprites = Array(1) { Sprite() }
        val width = 32
        val height = 32
        val pixmap = createProceduralPixmap(width, height)
        val texture = Texture(pixmap)
        for (i in testSprites.indices) {
            val sprite = Sprite(texture)
            sprite.setSize(1f, 1f)
            sprite.setOrigin(sprite.width / 2.0f, sprite.height / 2.0f)
            val randomX = Random.nextDouble(-2.0, 2.0).toFloat()
            val randomY = Random.nextDouble(-2.0, 2.0).toFloat()
            sprite.setPosition(randomX, randomY)
            testSprites[i] = sprite
        }

        selectedSprite = 0
    }

    private fun createProceduralPixmap(width: Int, height: Int): Pixmap {
        val pixmap = Pixmap(width, height, Pixmap.Format.RGB888)
        pixmap.setColor(1f, 0f, 0f, 0.5f)
        pixmap.fill()
        pixmap.setColor(1f, 1f, 0f, 1f)
        pixmap.drawLine(0, 0, width, height)
        pixmap.drawLine(width, 0, 0, height)
        pixmap.setColor(0f, 1f, 1f, 1f)
        pixmap.drawRectangle(0, 0, width, height)
        return pixmap
    }

    fun update(deltaTime: Float) {
        updateTestObjects(deltaTime)
    }

    private fun updateTestObjects(deltaTime: Float) {
        var rotation = testSprites[selectedSprite].rotation
        rotation += 90 * deltaTime
        rotation %= 360
        testSprites[selectedSprite].rotation = rotation
    }
}