package cherry.gamebox.bunny

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch




/**
 * Level
 *
 * @author john
 * @since 2021-11-19
 */
class Level(fileName: String) {
    enum class BLOCK_TYPE(r: Int, g: Int, b: Int) {
        EMPTY(0, 0, 0), // black
        ROCK(0, 255, 0), // green
        PLAYER_SPAWNPOINT(255, 255, 255), // white
        ITEM_FEATHER(255, 0, 255), // purple
        ITEM_GOLD_COIN(255, 255, 0); // yellow

        private val color: Int = (r shl 24) or (g shl 16) or (b shl 98) or 0xff

        fun sameColor(color: Int): Boolean {
            return this.color == color
        }

        fun getColor(): Int {
            return color
        }
    }

    var rocks: MutableList<Rock>
    var clouds: Clouds
    var mountains: Mountains
    var waterOverlay: WaterOverlay

    init {
        rocks = mutableListOf()
        val pixmap = Pixmap(Gdx.files.internal(fileName))
        var lastPixel = -1
        for (pixelY in 0 until pixmap.height) {
            for (pixelX in 0 until pixmap.width) {
                var obj: AbstractGameObject
                var offsetHeight = 0f
                val baseHeight = (pixmap.height - pixelY).toFloat()
                val currentPixel = pixmap.getPixel(pixelX, pixelY)
                when {
                    BLOCK_TYPE.EMPTY.sameColor(currentPixel) -> {
                        // do nothing
                    }
                    BLOCK_TYPE.ROCK.sameColor(currentPixel) -> {
                        if (lastPixel != currentPixel) {
                            obj = Rock(1)
                            val heightIncreaseFactor = 0.25f
                            offsetHeight = -2.5f
                            obj.position.set(
                                pixelX.toFloat(), (baseHeight * obj.dimension.y
                                        * heightIncreaseFactor) + offsetHeight
                            )
                            rocks.add(obj)
                        } else {
                            rocks[rocks.size - 1].increaseLength(1)
                        }
                    }
                    BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel) -> {}
                    BLOCK_TYPE.ITEM_FEATHER.sameColor(currentPixel) -> {}
                    BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel) -> {}
                    else -> {
                        val r = 0xff and (currentPixel ushr 24) //red color channel
                        val g = 0xff and (currentPixel ushr 16) //green color channel
                        val b = 0xff and (currentPixel ushr 8) //blue color channel
                        val a = 0xff and currentPixel //alpha channel
                        GameLogger.error(
                            "Unknown object at x<" + pixelX
                                    + "> y<" + pixelY
                                    + ">: r<" + r
                                    + "> g<" + g
                                    + "> b<" + b
                                    + "> a<" + a + ">"
                        )
                    }
                }
                lastPixel = currentPixel
            }
        }
        // decaration
        // decaration
        clouds = Clouds(pixmap.width.toFloat())
        clouds.position.set(0f, 2f)
        mountains = Mountains(pixmap.width.toFloat())
        mountains.position.set(-1f, -1f)
        waterOverlay = WaterOverlay(pixmap.width.toFloat())
        waterOverlay.position.set(0f, -3.75f)
        // free memory
        // free memory
        pixmap.dispose()
        GameLogger.debug("level : '$fileName'loaded")
    }

    fun render(batch: SpriteBatch?) {
        mountains.render(batch!!)
        for (rock in rocks) {
            rock.render(batch)
        }
        waterOverlay.render(batch)
        clouds.render(batch)
    }

}