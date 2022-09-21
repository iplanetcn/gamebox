package cherry.gamebox.bunny.game

import cherry.gamebox.bunny.game.objects.*
import cherry.gamebox.core.GameLogger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Array

/**
 * Level
 *
 * @author john
 * @since 2021-11-19
 */
class Level(filename: String) {

    enum class BLOCK_TYPE(r: Int, g: Int, b: Int) {
        EMPTY(0, 0, 0),  // black
        GOAL(255, 0, 0),  // red
        ROCK(0, 255, 0),  // green
        PLAYER_SPAWN_POINT(255, 255, 255),  // white
        ITEM_FEATHER(255, 0, 255),  // purple
        ITEM_GOLD_COIN(255, 255, 0);

        // yellow
        val color: Int

        init {
            color = r shl 24 or (g shl 16) or (b shl 8) or 0xff
        }

        fun sameColor(color: Int): Boolean {
            return this.color == color
        }
    }

    // player character
    var bunnyHead: BunnyHead? = null

    // objects
    var rocks: Array<Rock>
    var goldcoins: Array<GoldCoin>
    var feathers: Array<Feather>
    var carrots: Array<Carrot>

    // decoration
    var clouds: Clouds
    var mountains: Mountains
    var waterOverlay: WaterOverlay
    var goal: Goal? = null

    // book
    var helpTilt: Help
    var helpTouch: Help
    var helpFly: Help

    init {
        // player character
        bunnyHead = null

        // objects
        rocks = Array()
        goldcoins = Array()
        feathers = Array()
        carrots = Array()

        // load image file that represents the level data
        val pixmap = Pixmap(Gdx.files.internal(filename))
        // scan pixels from top-left to bottom-right
        var lastPixel = -1
        for (pixelY in 0 until pixmap.height) {
            for (pixelX in 0 until pixmap.width) {
                var obj: AbstractGameObject? = null
                var offsetHeight = 0f
                // height grows from bottom to top
                val baseHeight = (pixmap.height - pixelY).toFloat()
                // get color of current pixel as 32-bit RGBA value
                val currentPixel = pixmap.getPixel(pixelX, pixelY)
                // find matching color value to identify block type at (x,y)
                // point and create the corresponding game object if there is
                // a match

                // empty space
                if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {
                    // do nothing
                } else if (BLOCK_TYPE.ROCK.sameColor(currentPixel)) {
                    if (lastPixel != currentPixel) {
                        obj = Rock()
                        val heightIncreaseFactor = 0.25f
                        offsetHeight = -2.5f
                        obj.position[pixelX.toFloat()] =
                            baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight
                        rocks.add(obj as Rock?)
                    } else {
                        rocks!![rocks!!.size - 1].increaseLength(1)
                    }
                } else if (BLOCK_TYPE.PLAYER_SPAWN_POINT.sameColor(currentPixel)) {
                    obj = BunnyHead()
                    offsetHeight = -3.0f
                    obj.position[pixelX.toFloat()] = baseHeight * obj.dimension.y + offsetHeight
                    bunnyHead = obj
                } else if (BLOCK_TYPE.ITEM_FEATHER.sameColor(currentPixel)) {
                    obj = Feather()
                    offsetHeight = -1.5f
                    obj.position[pixelX.toFloat()] = baseHeight * obj.dimension.y + offsetHeight
                    feathers.add(obj as Feather?)
                } else if (BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel)) {
                    obj = GoldCoin()
                    offsetHeight = -1.5f
                    obj.position[pixelX.toFloat()] = baseHeight * obj.dimension.y + offsetHeight
                    goldcoins.add(obj as GoldCoin?)
                } else if (BLOCK_TYPE.GOAL.sameColor(currentPixel)) {
                    obj = Goal()
                    offsetHeight = -7.0f
                    obj.position[pixelX.toFloat()] = baseHeight + offsetHeight
                    goal = obj
                } else {
                    // red color channel
                    val r = 0xff and (currentPixel ushr 24)
                    // green color channel
                    val g = 0xff and (currentPixel ushr 16)
                    // blue color channel
                    val b = 0xff and (currentPixel ushr 8)
                    // alpha channel
                    val a = 0xff and currentPixel
                    GameLogger.error(
                        "Unknown object at x<$pixelX> y<$pixelY>: r<$r> g<$g> b<$b> a<$a>"
                    )
                }
                lastPixel = currentPixel
            }
        }

        // decoration
        clouds = Clouds(pixmap.width.toFloat())
        clouds!!.position[0f] = 2f
        mountains = Mountains(pixmap.width.toFloat())
        mountains!!.position[-1f] = -1f
        waterOverlay = WaterOverlay(pixmap.width.toFloat())
        waterOverlay!!.position[0f] = -3.75f

        // book
        helpTilt = Help()
        helpTilt!!.animation = Assets.book.animHelpTilt
        helpTilt!!.position.y = 10000f
        helpTouch = Help()
        helpTouch!!.animation = Assets.book.animHelpTouch
        helpTouch!!.position.y = 10000f
        helpFly = Help()
        helpFly!!.animation = Assets.book.animHelpFly
        helpFly!!.position.y = 10000f

        // free memory
        pixmap.dispose()
        GameLogger.debug("level '$filename' loaded")
    }

    fun update(deltaTime: Float) {
        // Bunny Head
        bunnyHead!!.update(deltaTime)
        // Rocks
        for (rock in rocks!!) rock.update(deltaTime)
        // Gold Coins
        for (goldCoin in goldcoins!!) goldCoin.update(deltaTime)
        // Feathers
        for (feather in feathers!!) feather.update(deltaTime)
        for (carrot in carrots!!) carrot.update(deltaTime)
        // Clouds
        clouds!!.update(deltaTime)
        // Help
        helpTilt!!.update(deltaTime)
        helpTouch!!.update(deltaTime)
        helpFly!!.update(deltaTime)
    }

    fun render(batch: SpriteBatch?) {
        // Draw Mountains
        mountains!!.render(batch!!)
        // Draw Goal
        goal!!.render(batch)
        // Draw Help
        helpTilt!!.render(batch)
        helpTouch!!.render(batch)
        helpFly!!.render(batch)
        // Draw Rocks
        for (rock in rocks!!) rock.render(batch)
        // Draw Gold Coins
        for (goldCoin in goldcoins!!) goldCoin.render(batch)
        // Draw Feathers
        for (feather in feathers!!) feather.render(batch)
        // Draw Carrots
        for (carrot in carrots!!) carrot.render(batch)
        // Draw Player Character
        bunnyHead!!.render(batch)
        // Draw Water Overlay
        waterOverlay!!.render(batch)
        // Draw Clouds
        clouds!!.render(batch)
    }
}