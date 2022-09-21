package cherry.gamebox.bunny.game

import cherry.gamebox.bunny.game.objects.*
import cherry.gamebox.core.GameLogger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch


/**
 * Level
 *
 * @author john
 * @since 2021-11-19
 */
class Level(filename: String) {
    enum class BlockType(r: Int, g: Int, b: Int) {
        EMPTY(0, 0, 0),  // black
        ROCK(0, 255, 0),  // green
        PLAYER_SPAWN_POINT(255, 255, 255),  // white
        ITEM_FEATHER(255, 0, 255),  // purple
        ITEM_GOLD_COIN(255, 255, 0);

        // yellow
        private val color: Int

        init {
            color = r shl 24 or (g shl 16) or (b shl 8) or 0xff
        }

        fun sameColor(color: Int): Boolean {
            return this.color == color
        }
    }

    // objects
    var bunnyHead: BunnyHead? = null
    var goldcoins: MutableList<GoldCoin>
    var feathers: MutableList<Feather>
    var rocks: MutableList<Rock>

    // decoration
    private var clouds: Clouds
    var mountains: Mountains
    var waterOverlay: WaterOverlay

    init {
        bunnyHead = null
        // objects
        rocks = mutableListOf()
        goldcoins = mutableListOf()
        feathers = mutableListOf()


        // load image file that represents the level data
        val pixmap = Pixmap(Gdx.files.internal(filename))

        // scan pixels from top-left to bottom-right
        var lastPixel = -1
        for (pixelY in 0 until pixmap.height) {
            for (pixelX in 0 until pixmap.width) {
                var obj: AbstractGameObject?
                var offsetHeight: Float

                // height grows from bottom to top
                val baseHeight = (pixmap.height - pixelY).toFloat()

                // get color of current pixel as 32-bit RGBA value
                val currentPixel = pixmap.getPixel(pixelX, pixelY)

                // find matching color value to identify block type at (x,y)
                // point and create the corresponding game object if there is
                // a match

                // empty space
                if (BlockType.EMPTY.sameColor(currentPixel)) {
                    // do nothing
                } else if (BlockType.ROCK.sameColor(currentPixel)) {
                    if (lastPixel != currentPixel) {
                        obj = Rock()
                        val heightIncreaseFactor = 0.25f
                        offsetHeight = -2.5f
                        obj.position[pixelX.toFloat()] =
                            baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight
                        rocks.add(obj)
                    } else {
                        rocks[rocks.size - 1].increaseLength(1)
                    }
                } else if (BlockType.PLAYER_SPAWN_POINT.sameColor(currentPixel)) {
                    obj = BunnyHead()
                    offsetHeight = -3.0f
                    obj.position[pixelX.toFloat()] = baseHeight * obj.dimension.y + offsetHeight
                    bunnyHead = obj
                } else if (BlockType.ITEM_FEATHER.sameColor(currentPixel)) {
                    obj = Feather()
                    offsetHeight = -1.5f
                    obj.position[pixelX.toFloat()] = baseHeight * obj.dimension.y + offsetHeight
                    feathers.add(obj)
                } else if (BlockType.ITEM_GOLD_COIN.sameColor(currentPixel)) {
                    obj = GoldCoin()
                    offsetHeight = -1.5f
                    obj.position[pixelX.toFloat()] = baseHeight * obj.dimension.y + offsetHeight
                    goldcoins.add(obj)
                } else {
                    // red color channel
                    val r = 0xff and (currentPixel ushr 24)
                    // green color channel
                    val g = 0xff and (currentPixel ushr 16)
                    // blue color channel
                    val b = 0xff and (currentPixel ushr 8)
                    // alpha channel
                    val a = 0xff and currentPixel
                    GameLogger.debug(
                        "Unknown object at x<" + pixelX + "> y<" + pixelY + ">: r<" + r + "> g<" + g + "> b<" + b
                                + "> a<" + a + ">"
                    )
                }
                lastPixel = currentPixel
            }
        }

        // decoration
        clouds = Clouds(pixmap.width.toFloat())
        clouds.position[0f] = 2f
        mountains = Mountains(pixmap.width.toFloat())
        mountains.position[-1f] = -1f
        waterOverlay = WaterOverlay(pixmap.width.toFloat())
        waterOverlay.position[0f] = -3.75f

        // free memory
        pixmap.dispose()
       GameLogger.debug("level '$filename' loaded")
    }

    fun render(batch: SpriteBatch?) {
        // Draw Mountains
        mountains.render(batch!!)

        // Draw Rocks
        for (rock in rocks) rock.render(batch)

        // Draw Gold Coins
        for (goldCoin in goldcoins) goldCoin.render(batch)

        // Draw Feathers
        for (feather in feathers) feather.render(batch)

        // Draw Player Character
        bunnyHead!!.render(batch)

        // Draw Water Overlay
        waterOverlay.render(batch)

        // Draw Clouds
        clouds.render(batch)
    }

    fun update(deltaTime: Float) {
        bunnyHead!!.update(deltaTime)
        for (rock in rocks) rock.update(deltaTime)
        for (goldCoin in goldcoins) goldCoin.update(deltaTime)
        for (feather in feathers) feather.update(deltaTime)
        clouds.update(deltaTime)
    }
}