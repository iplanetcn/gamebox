package cherry.gamebox.link

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3

class LinkGame : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var image: Texture
    lateinit var sound: Sound
    lateinit var music: Music
    lateinit var textureAtlas: TextureAtlas
    lateinit var background: Sprite
    lateinit var camera: OrthographicCamera
    lateinit var packAtlas: TextureAtlas
    lateinit var currentIcon: TextureAtlas.AtlasRegion
    lateinit var pos: Vector3
    lateinit var shapeRenderer: ShapeRenderer
    lateinit var packSetList: ArrayList<TextureAtlas.AtlasRegion>

    override fun create() {
        packSetList = ArrayList()
        batch = SpriteBatch()
        image = Texture("badlogic.jpg")

        sound = Gdx.audio.newSound(Gdx.files.internal("audio/match.wav"))
        music = Gdx.audio.newMusic(Gdx.files.internal("audio/music.mp3"))

        music.isLooping = true
        music.play()

        textureAtlas = TextureAtlas("graphic/frames.pack")
        packAtlas = TextureAtlas("package/pack2.pack")
        for (i in 1..64) {
            packSetList.add(packAtlas.findRegion("set$i"))
        }
        currentIcon = packAtlas.findRegion("set1")

        background = textureAtlas.createSprite("background")
        background.setPosition(0F, 0F)

        camera = OrthographicCamera()
        camera.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        pos = Vector3(Gdx.graphics.width.toFloat() / 2, Gdx.graphics.height.toFloat() / 2, 0F)

        shapeRenderer = ShapeRenderer()

    }

    override fun render() {
        // logic
        camera.update()
        if (Gdx.input.isTouched) {
            pos.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0F)
            camera.unproject(pos)
            sound.play()
            if ((packSetList.size > 0)) {
                currentIcon = packSetList.removeLast()
            }
        }

        // drawing
        Gdx.gl.glClearColor(1F, 1F, 1F, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.projectionMatrix = camera.combined
        batch.begin()
        background.draw(batch)
        batch.draw(image, 0F, 0F)
        batch.draw(
            currentIcon,
            pos.x,
            pos.y
        )


        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        image.dispose()
        sound.dispose()
        music.dispose()
        shapeRenderer.dispose()
    }

}