package cherry.gamebox.fruit

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.TimeUtils
import kotlin.random.Random

class GameScreen(private val game: FruitGame) : Screen {
    private var bucketImage = Texture("basket.png")
    private var matchSound = Gdx.audio.newSound(Gdx.files.internal("audio/match.wav"))
    private var boostSound = Gdx.audio.newSound(Gdx.files.internal("audio/boost.wav"))
    private var rainMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music.mp3"))
    private var camera = OrthographicCamera()
    private var bucket = Rectangle()
    private var raindrops = Array<Pair<Rectangle, Int>>()
    private var lastDropTime: Long = 0
    private var dropsGathered = 0
    private var packs = ArrayList<ArrayList<TextureAtlas.AtlasRegion>>()
    private var atlasList = ArrayList<TextureAtlas>()
    private var iconList: ArrayList<TextureAtlas.AtlasRegion>

    init {
        rainMusic.isLooping = true
        camera.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        bucket.x = Gdx.graphics.width / 2f - 64 / 2f
        bucket.y = 64f
        bucket.width = 64f * 2
        bucket.height = 64f * 2
        for (i in 1..5) {
            val atlas = TextureAtlas("package/pack$i.pack")
            val pack =  ArrayList<TextureAtlas.AtlasRegion>()
            for (j in 1..64) {
                pack.add(atlas.findRegion("set$j"))
            }
            atlasList.add(atlas)
            packs.add(pack)
        }
        iconList = packs[Random.nextInt(1, 5)]
        spawnRaindrop()
    }

    private fun spawnRaindrop() {
        val raindrop = Rectangle()
        raindrop.x = MathUtils.random(0, Gdx.graphics.width - 64).toFloat()
        raindrop.y = Gdx.graphics.height.toFloat()
        raindrop.width = 64f
        raindrop.height = 64f
        raindrops.add(Pair(raindrop, Random.nextInt(1, 64)))
        lastDropTime = TimeUtils.nanoTime()
    }

    override fun render(delta: Float) {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0.298f, 0.686f, 0.314f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // tell the camera to update its matrices.
        camera.update()

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.projectionMatrix = camera.combined

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin()
        game.font.color = Color.DARK_GRAY
        game.font.draw(
            game.batch,
            "Score: $dropsGathered",
            64f,
            Gdx.graphics.height.toFloat() - 24f
        )

        game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height)
        for ((raindrop, index) in raindrops) {
            game.batch.draw(iconList[index], raindrop.x, raindrop.y)
        }
        game.batch.end()

        // process user input
        if (Gdx.input.isTouched) {
            val touchPos = Vector3()
            touchPos[Gdx.input.x.toFloat(), Gdx.input.y.toFloat()] = 0f
            camera.unproject(touchPos)
//            bucket.x = touchPos.x - 64 / 2f
            bucket.x = touchPos.x - 64 / 2f
            bucket.y = touchPos.y - 64 / 2f
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.deltaTime
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.deltaTime

        // make sure the bucket stays within the screen bounds
        if (bucket.x < 0) bucket.x = 0f
        if (bucket.x > Gdx.graphics.width - 64) bucket.x = (Gdx.graphics.width - 64).toFloat()

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop()

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        val it: MutableIterator<Pair<Rectangle, Int>> = raindrops.iterator()
        while (it.hasNext()) {
            val raindrop = it.next().first
            raindrop.y -= 200 * Gdx.graphics.deltaTime
            if (raindrop.y + 64 < 0) it.remove()
            if (raindrop.overlaps(bucket)) {
                dropsGathered++
                matchSound.play()
                it.remove()
            }
        }
    }

    override fun resize(width: Int, height: Int) {}
    override fun show() {
        // start the playback of the background music
        // when the screen is shown
        rainMusic.play()
    }

    override fun hide() {}
    override fun pause() {}
    override fun resume() {}
    override fun dispose() {
        boostSound.dispose()
        matchSound.dispose()
        rainMusic.dispose()
        for (atlas in atlasList) {
            atlas.dispose()
        }
    }
}