package cherry.gamebox.bunny.screen

import cherry.gamebox.bunny.game.Assets
import cherry.gamebox.bunny.screen.transitions.ScreenTransitionFade
import cherry.gamebox.bunny.util.AudioManager
import cherry.gamebox.bunny.util.CharacterSkin
import cherry.gamebox.bunny.util.Constants
import cherry.gamebox.bunny.util.GamePreferences
import cherry.gamebox.core.GameLogger
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha
import com.badlogic.gdx.scenes.scene2d.actions.Actions.delay
import com.badlogic.gdx.scenes.scene2d.actions.Actions.forever
import com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy
import com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo
import com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel
import com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateBy
import com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateTo
import com.badlogic.gdx.scenes.scene2d.actions.Actions.run
import com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo
import com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence
import com.badlogic.gdx.scenes.scene2d.actions.Actions.touchable
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Slider
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.StretchViewport


/**
 * MenuScreen
 *
 * @author john
 * @since 2022-09-21
 */
private const val DEBUG_REBUILD_INTERVAL = 5.0f
class MenuScreen(game: DirectedGame) : AbstractGameScreen(game) {
    private lateinit var stage: Stage
    private lateinit var skinCanyonBunny: Skin
    private lateinit var skinLibgdx: Skin

    // menu
    private lateinit var imgBackground: Image
    private lateinit var imgLogo: Image
    private lateinit var imgInfo: Image
    private lateinit var imgCoins: Image
    private lateinit var imgBunny: Image
    private lateinit var btnMenuPlay: Button
    private lateinit var btnMenuOptions: Button
    private lateinit var imgBookCover: Image
    private lateinit var lblBookMusicCredits: Label

    // options
    private lateinit var winOptions: Window
    private lateinit var btnWinOptSave: TextButton
    private lateinit var btnWinOptCancel: TextButton
    private lateinit var chkSound: CheckBox
    private lateinit var sldSound: Slider
    private lateinit var chkMusic: CheckBox
    private lateinit var sldMusic: Slider
    private lateinit var selCharSkin: SelectBox<CharacterSkin>
    private lateinit var imgCharSkin: Image
    private lateinit var chkShowFpsCounter: CheckBox

    // debug
    private val debugEnabled = false
    private var debugRebuildStage = 0f


    override fun render(deltaTime: Float) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        if (debugEnabled) {
            debugRebuildStage -= deltaTime
            if (debugRebuildStage <= 0) {
                debugRebuildStage = DEBUG_REBUILD_INTERVAL
                rebuildStage()
            }
        }
        stage.act(deltaTime)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun show() {
        stage = Stage(
            StretchViewport(
                Constants.VIEWPORT_GUI_WIDTH,
                Constants.VIEWPORT_GUI_HEIGHT
            )
        )
        Gdx.input.inputProcessor = stage
        rebuildStage()
    }

    override fun hide() {
        stage.dispose()
        skinCanyonBunny.dispose()
    }

    override fun pause() {}
    override fun getInputProcessor(): InputProcessor {
        return stage
    }

    private fun rebuildStage() {
        skinCanyonBunny = Skin(
            Gdx.files.internal(Constants.SKIN_CANYONBUNNY_UI),
            TextureAtlas(Constants.TEXTURE_ATLAS_UI)
        )
        skinLibgdx = Skin(
            Gdx.files.internal(Constants.SKIN_LIBGDX_UI),
            TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI)
        )

        // build all layers
        val layerBackground = buildBackgroundLayer()
        val layerObjects = buildObjectsLayer()
        val layerLogos = buildLogosLayer()
        val layerControls = buildControlsLayer()
        val layerOptionsWindow = buildOptionsWindowLayer()
        val layerBook = buildBookLayer()
        val layerMusicCredits = buildMusicCreditsLayer()

        // assemble stage for menu screen
        stage.clear()
        val stack = Stack()
        stage.addActor(stack)
        stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT)
        stack.add(layerBackground)
        stack.add(layerObjects)
        stack.add(layerLogos)
        stack.add(layerControls)

        stage.addActor(layerBook)
        stage.addActor(layerMusicCredits)
        stage.addActor(layerOptionsWindow)
    }

    private fun buildBackgroundLayer(): Table {
        val layer = Table()
        imgBackground = Image(skinCanyonBunny, "background")
        layer.add<Actor>(imgBackground)
        return layer
    }

    private fun buildObjectsLayer(): Table {
        val layer = Table()

        // + Coins
        imgCoins = Image(skinCanyonBunny, "coins")
        layer.addActor(imgCoins)
        imgCoins.setPosition(135f, 80f)

        // + Bunny
        imgBunny = Image(skinCanyonBunny, "bunny")
        layer.addActor(imgBunny)
        imgBunny.setPosition(355f, 40f)
        return layer
    }

    private fun buildLogosLayer(): Table {
        val layer = Table()
        layer.left().top()

        // + Game Logo
        imgLogo = Image(skinCanyonBunny, "logo")
        layer.add<Actor>(imgLogo)
        layer.row().expandY()

        // + Info Logos
        imgInfo = Image(skinCanyonBunny, "info")
        layer.add<Actor>(imgInfo).bottom()
        if (debugEnabled) layer.debug()
        return layer
    }

    private fun buildControlsLayer(): Table {
        val layer = Table()
        layer.right().bottom()

        // + Play Button
        btnMenuPlay = Button(skinCanyonBunny, "play")
        layer.add<Actor>(btnMenuPlay)
        btnMenuPlay.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                onPlayClicked()
            }
        })
        layer.row()

        // + Options Button
        btnMenuOptions = Button(skinCanyonBunny, "options")
        layer.add<Actor>(btnMenuOptions)
        btnMenuOptions.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                onOptionsClicked()
            }
        })
        if (debugEnabled) layer.debug()
        return layer
    }

    private fun onPlayClicked() {
        game.setScreen(GameScreen(game), ScreenTransitionFade.init(0.75f))
    }

    private fun buildOptWinAudioSettings(): Table {
        val tbl = Table()
        // + Title: "Audio"
        tbl.pad(10f, 10f, 0f, 10f)
        tbl.add<Actor>(Label("Audio", skinLibgdx, "default-font", Color.ORANGE)).colspan(3)
        tbl.row()
        tbl.columnDefaults(0).padRight(10f)
        tbl.columnDefaults(1).padRight(10f)
        // + Checkbox, "Sound" label, sound volume slider
        chkSound = CheckBox("", skinLibgdx)
        tbl.add<Actor>(chkSound)
        tbl.add<Actor>(Label("Sound", skinLibgdx))
        sldSound = Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx)
        tbl.add<Actor>(sldSound)
        tbl.row()
        // + Checkbox, "Music" label, music volume slider
        chkMusic = CheckBox("", skinLibgdx)
        tbl.add<Actor>(chkMusic)
        tbl.add<Actor>(Label("Music", skinLibgdx))
        sldMusic = Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx)
        tbl.add<Actor>(sldMusic)
        tbl.row()
        return tbl
    }

    private fun buildOptWinSkinSelection(): Table {
        val tbl = Table()
        // + Title: "Character Skin"
        tbl.pad(10f, 10f, 0f, 10f)
        tbl.add<Actor>(Label("Character Skin", skinLibgdx, "default-font", Color.ORANGE)).colspan(2)
        tbl.row()
        // + Drop down box filled with skin items
        selCharSkin = SelectBox(skinLibgdx)
        if (Gdx.app.type == Application.ApplicationType.WebGL) {
            val items = com.badlogic.gdx.utils.Array<CharacterSkin>()
            val arr = CharacterSkin.entries.toTypedArray()
            for (i in arr.indices) {
                items[i] = (arr[i])
            }
            selCharSkin.items = items
        } else {
            selCharSkin.setItems(*CharacterSkin.entries.toTypedArray())
        }
        selCharSkin.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor) {
                onCharSkinSelected((actor as SelectBox<*>).selectedIndex)
            }
        })
        tbl.add(selCharSkin).width(120f).padRight(20f)
        // + Skin preview image
        imgCharSkin = Image(Assets.bunny.head)
        tbl.add<Actor>(imgCharSkin).width(50f).height(50f)
        return tbl
    }

    private fun buildOptWinDebug(): Table {
        val tbl = Table()
        // + Title: "Debug"
        tbl.pad(10f, 10f, 0f, 10f)
        tbl.add<Actor>(Label("Debug", skinLibgdx, "default-font", Color.RED)).colspan(3)
        tbl.row()
        tbl.columnDefaults(0).padRight(10f)
        tbl.columnDefaults(1).padRight(10f)
        // + Checkbox, "Show FPS Counter" label
        chkShowFpsCounter = CheckBox("", skinLibgdx)
        tbl.add<Actor>(Label("Show FPS Counter", skinLibgdx))
        tbl.add<Actor>(chkShowFpsCounter)
        tbl.row()
        return tbl
    }

    private fun buildOptWinButtons(): Table {
        val tbl = Table()
        // + Separator
        var lbl = Label("", skinLibgdx)
        lbl.setColor(0.75f, 0.75f, 0.75f, 1f)
        lbl.style = LabelStyle(lbl.style)
        lbl.style.background = skinLibgdx.newDrawable("white")
        tbl.add<Actor>(lbl).colspan(2).height(1f).width(220f).pad(0f, 0f, 0f, 1f)
        tbl.row()
        lbl = Label("", skinLibgdx)
        lbl.setColor(0.5f, 0.5f, 0.5f, 1f)
        lbl.style = LabelStyle(lbl.style)
        lbl.style.background = skinLibgdx.newDrawable("white")
        tbl.add<Actor>(lbl).colspan(2).height(1f).width(220f).pad(0f, 1f, 5f, 0f)
        tbl.row()
        // + Save Button with event handler
        btnWinOptSave = TextButton("Save", skinLibgdx)
        tbl.add(btnWinOptSave).padRight(30f)
        btnWinOptSave.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                onSaveClicked()
            }
        })
        // + Cancel Button with event handler
        btnWinOptCancel = TextButton("Cancel", skinLibgdx)
        tbl.add(btnWinOptCancel)
        btnWinOptCancel.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                onCancelClicked()
            }
        })
        return tbl
    }

    private fun buildBookLayer(): Actor {
        val delayStart = 10f
        val delayLoopMin = 3f
        val delayLoopMax = 6f
        imgBookCover = Image(skinCanyonBunny, "book-cover")
        imgBookCover.setOrigin(imgBookCover.width / 2, imgBookCover.height / 2)

        GameLogger.debug("imageBookCover (width, height): (${imgBookCover.width}, ${imgBookCover.height})")
        val scaleImage = 1f
        val scaleUpSize = scaleImage * 1.25f
        val scaleUpDur = 0.025f
        val scaleDownDur = 0.25f

        imgBookCover.addAction(
            sequence(
                moveTo(
                    -imgBookCover.width,
                    (Constants.VIEWPORT_GUI_HEIGHT - imgBookCover.height) / 2
                ),
                moveBy(0f, -20f),
                scaleTo(0f, 0f),
                delay(delayStart),
                parallel(
                    moveBy(imgBookCover.width * 1.2f, 0f, 1f, Interpolation.swingOut),
                    scaleTo(1 * scaleImage, 1 * scaleImage, 0.75f, Interpolation.sine)
                ),
                forever(
                    sequence(
                        delay(MathUtils.random(delayLoopMin, delayLoopMax)),
                        scaleTo(scaleUpSize, scaleUpSize, scaleUpDur, Interpolation.sine),
                        scaleTo(1 * scaleImage, 1 * scaleImage, scaleDownDur, Interpolation.sineIn),
                        scaleTo(scaleUpSize, scaleUpSize, scaleUpDur, Interpolation.sine),
                        scaleTo(1 * scaleImage, 1 * scaleImage, scaleDownDur, Interpolation.sineIn),
                        delay(MathUtils.random(delayLoopMin, delayLoopMax)),
                        rotateBy(360f * 2, 1f, Interpolation.swing),
                        rotateTo(0f)
                    )
                )
            )
        )

        imgBookCover.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                Gdx.net.openURI("http://libgdx.gamerald.com/")
            }
        })

        return imgBookCover
    }


    private fun buildMusicCreditsLayer(): Actor {
        val delay = 13f
        val moveDistance = 10f
        lblBookMusicCredits = Label("Music by Klaus \"keith303\" Spang", skinLibgdx[LabelStyle::class.java])
        lblBookMusicCredits.setPosition(380f, 0f)
        lblBookMusicCredits.addAction(
            sequence(
                moveBy(0f, -50f), alpha(0f),
                delay(delay),
                parallel(moveBy(0f, +50 + moveDistance, 1f, Interpolation.linear), alpha(0.8f, 1.5f))
            )
        )
        return lblBookMusicCredits
    }

    private fun buildOptionsWindowLayer(): Table {
        winOptions = Window("Options", skinLibgdx)
        // + Audio Settings: Sound/Music CheckBox and Volume Slider
        winOptions.add(buildOptWinAudioSettings()).row()
        // + Character Skin: Selection Box (White, Gray, Brown)
        winOptions.add(buildOptWinSkinSelection()).row()
        // + Debug: Show FPS Counter
        winOptions.add(buildOptWinDebug()).row()
        // + Separator and Buttons (Save, Cancel)
        winOptions.add(buildOptWinButtons()).pad(10f, 0f, 10f, 0f)

        // Make options window slightly transparent
        winOptions.setColor(1f, 1f, 1f, 0.8f)
        // Hide options window by default
        showOptionsWindow(visible = false, animated = false)
        if (debugEnabled) winOptions.debug()
        // Let TableLayout recalculate widget sizes and positions
        winOptions.pack()
        // Move options window to bottom right corner
        winOptions.setPosition(Constants.VIEWPORT_GUI_WIDTH - winOptions.width - 50, 50f)
        return winOptions
    }

    private fun onOptionsClicked() {
        loadSettings()
        showMenuButtons(false)
        showOptionsWindow(visible = true, animated = true)
    }

    private fun onSaveClicked() {
        saveSettings()
        onCancelClicked()
        AudioManager.onSettingsUpdated()
    }

    private fun onCancelClicked() {
        showMenuButtons(true)
        showOptionsWindow(visible = false, animated = true)
        AudioManager.onSettingsUpdated()
    }

    private fun onCharSkinSelected(index: Int) {
        val skin = CharacterSkin.values()[index]
        imgCharSkin.color = skin.color
    }

    private fun loadSettings() {
        val prefs = GamePreferences
        prefs.load()
        chkSound.isChecked = prefs.sound
        sldSound.value = prefs.volSound
        chkMusic.isChecked = prefs.music
        sldMusic.value = prefs.volMusic
        selCharSkin.selectedIndex = prefs.charSkin
        onCharSkinSelected(prefs.charSkin)
        chkShowFpsCounter.isChecked = prefs.showFpsCounter
    }

    private fun saveSettings() {
        val prefs = GamePreferences
        prefs.sound = chkSound.isChecked
        prefs.volSound = sldSound.value
        prefs.music = chkMusic.isChecked
        prefs.volMusic = sldMusic.value
        prefs.charSkin = selCharSkin.selectedIndex
        prefs.showFpsCounter = chkShowFpsCounter.isChecked
        prefs.save()
    }

    private fun showMenuButtons(visible: Boolean) {
        val moveDuration = 1.0f
        val moveEasing: Interpolation = Interpolation.swing
        val delayOptionsButton = 0.25f
        val moveX = (300 * if (visible) -1 else 1).toFloat()
        val moveY = 0f
        val touchEnabled = if (visible) Touchable.enabled else Touchable.disabled
        btnMenuPlay.addAction(moveBy(moveX, moveY, moveDuration, moveEasing))
        btnMenuOptions.addAction(
            sequence(
                delay(delayOptionsButton),
                moveBy(moveX, moveY, moveDuration, moveEasing)
            )
        )
        val seq = sequence()
        if (visible) seq.addAction(delay(delayOptionsButton + moveDuration))
        seq.addAction(run(Runnable {
            btnMenuPlay.touchable = touchEnabled
            btnMenuOptions.touchable = touchEnabled
        }))
        stage.addAction(seq)
    }

    private fun showOptionsWindow(visible: Boolean, animated: Boolean) {
        val alphaTo = if (visible) 0.8f else 0.0f
        val duration = if (animated) 1.0f else 0.0f
        val touchEnabled = if (visible) Touchable.enabled else Touchable.disabled
        winOptions.addAction(
            sequence(
                touchable(touchEnabled),
                alpha(alphaTo, duration)
            )
        )
    }
}